package com.bri.webfinal.service.impl;

import com.bri.inputData.entity.MetadataField;
import com.bri.inputData.entity.MetadataFieldDataType;
import com.bri.webfinal.config.RabbitMQConfig;
import com.bri.webfinal.controller.request.SyncAddRequest;
import com.bri.webfinal.enums.EventMessageType;
import com.bri.webfinal.language.MySqlVisitor;
import com.bri.webfinal.language.sqlLikeLexer;
import com.bri.webfinal.language.sqlLikeParser;
import com.bri.webfinal.model.DatasourcesDO;
import com.bri.webfinal.model.EventMessage;
import com.bri.webfinal.model.HeteroTech;
import com.bri.webfinal.model.科技平台DO;
import com.bri.webfinal.service.*;
import com.bri.webfinal.util.CommonUtil;
import com.bri.webfinal.util.JsonData;
import com.bri.webfinal.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@Service
@Slf4j
public class FunctionServiceImpl implements FunctionService {
    @Autowired
    private DatasourcesService datasourcesService;
    @Autowired
    private FileService fileService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    //连接数据库
    public Connection getConn(String url, String user, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.info("数据源驱动失败");
        }
        Connection conn= null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            log.info("数据库连接失败");
        }
        return conn;
    }

    public Connection getConnByDO(DatasourcesDO d)
    {
        String ip=d.getIp();
        String db=d.getDbname();
        String user1 = d.getUser();
        String password1=d.getPassword();
        String url1="jdbc:mysql://"+ip+":3306/"+db+"?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        return getConn(url1,user1,password1);
    }

    public List<HeteroTech> data_exchange(ResultSet rs, Connection conn2, String table2, Map<Set<MetadataField>,Set<MetadataField>> map) throws SQLException {
        //map2的元数据都在
        //先拿出map2所有列,即拿出所有的metaData
        Set<MetadataField> set_source=new HashSet<>();

        //队列保存元数据顺序
        Queue<MetadataField> queue=new LinkedList<>();

        map.forEach((keys,values)->{
            for(MetadataField key:keys)           //TODO 暂时就拿出一列
            {
                set_source.add(key);      //map2的所有列
                break;
            }
        });

        //构造sql
        StringBuilder sql= new StringBuilder("insert into " + table2 + "(");
        for(MetadataField source:set_source)
        {
            //得到每一个元数据列
            sql.append(source.getNameZH());
            sql.append(",");
            queue.add(source);
        }
        //去掉最后一个,
        sql.deleteCharAt(sql.length()-1);
        sql.append(") values(");
        for(int i=0;i< queue.size();i++)
        {
            if(i+1< queue.size())
                sql.append("?,");
            else
                sql.append("?");
        }
        sql.append(");");
        PreparedStatement ps=conn2.prepareStatement(sql.toString());

        //列是每个MetadatField的name
        int siz= queue.size();
        int batch_num=0;
        while (rs.next())
        {
            for(int i= siz;i>0;i--)
            {
                MetadataField insert_line=queue.poll();
                MetadataField line_title=new MetadataField();
                for(Set<MetadataField> lis:map.keySet())
                {
                    Set<MetadataField> s_meta=map.get(lis);
                    //TODO 默认都是一个
                    for(MetadataField li:lis)
                    {
                        if(li.equals(insert_line))
                        {
                            for(MetadataField s:s_meta)
                            {
                                line_title=s;
                                break;
                            }
                        }
                    }
                }
                queue.add(insert_line);        //TODO 不确定对不对
                //可从rs获得每一列的具体内容
                //line_title.getNameZH()：得到列名，比如科技平台
                //o是原数据的值
                if(line_title.getDataType().equals(MetadataFieldDataType.TEXT))
                {
                    String o=rs.getString(line_title.getNameZH());
                    if(insert_line.getDataType().equals(MetadataFieldDataType.NUMBER))
                    {
                        int s=Integer.parseInt(o);
                        ps.setInt(siz-i+1,s);
                    }
                    ps.setString(siz-i+1,o);
                }
                else if(line_title.getDataType().equals(MetadataFieldDataType.NUMBER))
                {
                    int o=rs.getInt(line_title.getNameZH());
                    if(insert_line.getDataType().equals(MetadataFieldDataType.TEXT))
                    {
                        //将int类型的o转成string
                        String s=Integer.toString(o);
                        ps.setString(siz-i+1,s);
                    }
                    else
                        ps.setInt(siz-i+1,o);
                }
                else if(line_title.getDataType().equals(MetadataFieldDataType.DATETIME))
                {
                    Date o=rs.getDate(line_title.getNameZH());
                    java.sql.Date d=new java.sql.Date(o.getTime());
                    ps.setDate(siz-i+1,d);
                }
            }
            //有完整sql了
            ps.addBatch();    //打包
            batch_num++;
            if(batch_num>10)
            {
                ps.executeBatch();
                ps.clearBatch();
                batch_num=0;
            }
        }
        ps.executeBatch();
        ps.clearBatch();

        List<HeteroTech> list=new ArrayList<>();

        //rowset遍历一下conn2
        String selectSql="select * from 科技平台";
        PreparedStatement ps2=conn2.prepareStatement(selectSql);
        ResultSet rs2=ps2.executeQuery();
        while (rs2.next())
        {
            String time=rs2.getString("科技平台服务最近发布日期");
            String code=rs2.getString("科技平台服务资源标识");
            String name=rs2.getString("科技平台服务资源名称");
            int information=rs2.getInt("科技平台服务服务方信息");
            String content=rs2.getString("科技平台服务内容");
            String res=rs2.getString("科技平台服务访问限制");
            list.add(new HeteroTech(time,code,name,information,content,res));
        }
        return list;
    }

    @Override
    //@Async("threadPoolTaskExecutor")
    //2转1
    public List<HeteroTech> exchange(int id1, int id2, File xml1,File xml2,String table_name) throws SQLException, IOException, ParserConfigurationException, SAXException {
        //先根据两个id读到数据源信息
        DatasourcesDO dataSourceDO1 = datasourcesService.detailDataSource(id1);
        DatasourcesDO dataSourceDO2 = datasourcesService.detailDataSource(id2);

        //根据数据源信息连接数据库
        Connection conn1=getConnByDO(dataSourceDO1);
        Connection conn2=getConnByDO(dataSourceDO2);

        //拿到conn1的结果集
        String select = "select * from "+table_name;
        Statement statement=conn1.createStatement();
        ResultSet rs = statement.executeQuery(select);

        //根据xmlName拿到表的元数据map
        Map<Set<MetadataField>,Set<MetadataField>> map1=fileService.readXML(xml1);
        Map<Set<MetadataField>,Set<MetadataField>> map2=fileService.readXML(xml2);
        Map<Set<MetadataField>,Set<MetadataField>> final_map=new HashMap<>();
        //得到这两个表的对应map
        //是要插入的，原数据
        map1.forEach((key,value)->{
            Set<MetadataField> value2=map2.get(key);
            if(value2!=null)         //有key的对应
            {
                //1转2
                //2,1
                final_map.put(value2,value);
            }
        });

        List<HeteroTech> list=data_exchange(rs,conn2,table_name,final_map);
        conn1.close();
        conn2.close();
        log.info("数据交换完毕");
        return list;
    }

    public List<科技平台DO> add_list(Map<Set<MetadataField>,Set<MetadataField>> map1,List<科技平台DO> list,ResultSet rs1) throws SQLException, ParseException {
        Set<MetadataField> set1;MetadataField m;
        String service_name=null,key_word=null,category=null,description=null,name=null,code=null;Date time=null;int restrain=0,information=0;
        while(rs1.next())
        {
            if((set1=contain_key_nameZH("科技平台服务名称",map1))!=null)
            {
                //取出映射的line_name
                m=get_value(map1,set1);
                if(m.getDataType().equals(MetadataFieldDataType.TEXT))
                    service_name=rs1.getString(m.getNameZH());
                else if(m.getDataType().equals(MetadataFieldDataType.NUMBER))
                    service_name=String.valueOf(rs1.getInt(m.getNameZH()));
            }
            if((set1=contain_key_nameZH("科技平台服务关键词",map1))!=null)
            {
                m=get_value(map1,set1);
                if(m.getDataType().equals(MetadataFieldDataType.TEXT))
                    key_word=rs1.getString(m.getNameZH());
                else if(m.getDataType().equals(MetadataFieldDataType.NUMBER))
                    key_word=String.valueOf(rs1.getInt(m.getNameZH()));
            }
            if((set1=contain_key_nameZH("科技平台服务分类",map1))!=null)
            {
                m=get_value(map1,set1);
                if(m.getDataType().equals(MetadataFieldDataType.TEXT))
                    category=rs1.getString(m.getNameZH());
                else if(m.getDataType().equals(MetadataFieldDataType.NUMBER))
                    category=String.valueOf(rs1.getInt(m.getNameZH()));
            }
            if((set1=contain_key_nameZH("科技平台服务最近发布日期",map1))!=null)
            {
                m=get_value(map1,set1);
                if(m.getDataType().equals(MetadataFieldDataType.DATETIME))
                    time=rs1.getDate(m.getNameZH());
                else if(m.getDataType().equals(MetadataFieldDataType.TEXT))
                {
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    time=sdf.parse(rs1.getString(m.getNameZH()));
                }
            }
            if((set1=contain_key_nameZH("科技平台服务内容描述",map1))!=null)
            {
                m=get_value(map1,set1);
                if(m.getDataType().equals(MetadataFieldDataType.TEXT))
                    description=rs1.getString(m.getNameZH());
                else if(m.getDataType().equals(MetadataFieldDataType.NUMBER))
                    description=String.valueOf(rs1.getInt(m.getNameZH()));
            }
            if((set1=contain_key_nameZH("科技平台服务访问限制",map1))!=null)
            {
                m=get_value(map1,set1);
                if(m.getDataType().equals(MetadataFieldDataType.NUMBER))
                    restrain=rs1.getInt(m.getNameZH());
                else if(m.getDataType().equals(MetadataFieldDataType.TEXT))
                    restrain=Integer.parseInt(rs1.getString(m.getNameZH()));
            }
            if((set1=contain_key_nameZH("科技平台服务资源名称",map1))!=null)
            {
                m=get_value(map1,set1);
                if(m.getDataType().equals(MetadataFieldDataType.TEXT))
                    name=rs1.getString(m.getNameZH());
                else if(m.getDataType().equals(MetadataFieldDataType.NUMBER))
                    name=String.valueOf(rs1.getInt(m.getNameZH()));
            }
            if((set1=contain_key_nameZH("科技平台服务资源标识代码",map1))!=null)
            {
                m=get_value(map1,set1);
                if(m.getDataType().equals(MetadataFieldDataType.TEXT))
                    code=rs1.getString(m.getNameZH());
                else if(m.getDataType().equals(MetadataFieldDataType.NUMBER))
                    code=String.valueOf(rs1.getInt(m.getNameZH()));
            }
            if((set1=contain_key_nameZH("科技平台服务服务方信息",map1))!=null)
            {
                m=get_value(map1,set1);
                if(m.getDataType().equals(MetadataFieldDataType.NUMBER))
                    information=rs1.getInt(m.getNameZH());
                else if(m.getDataType().equals(MetadataFieldDataType.TEXT))
                    information=Integer.parseInt(rs1.getString(m.getNameZH()));
            }
            list.add(new 科技平台DO(time,code,name,information,description,restrain,key_word,service_name,category));
        }

        return list;
    }

    @Override
    public List<科技平台DO> syncSelect(int id1, int id2, File file1, File file2, String selectSql) throws SQLException, IOException, ParserConfigurationException, SAXException, ParseException {
        //默认先只处理select * from Ident
        String table=MySqlVisitor.select_table;
//        String comparison=MySqlVisitor.comparison;
//        String line=MySqlVisitor.line_name;
//        String line_content=MySqlVisitor.select_content;
        //先根据两个id读到数据源信息
        DatasourcesDO dataSourceDO1 = datasourcesService.detailDataSource(id1);
        DatasourcesDO dataSourceDO2 = datasourcesService.detailDataSource(id2);

        Connection conn1=getConnByDO(dataSourceDO1);
        Connection conn2=getConnByDO(dataSourceDO2);

        //根据id1和file1找一下它的全部数据，并转成标准
//        String part1="select * from ";
//        String sql=part1+table;
        PreparedStatement ps=conn1.prepareStatement(selectSql);
        ResultSet rs1=ps.executeQuery();

        //根据xmlName拿到表的元数据map
        Map<Set<MetadataField>,Set<MetadataField>> map1=fileService.readXML(file1);
        Map<Set<MetadataField>,Set<MetadataField>> map2=fileService.readXML(file2);
        //TODO 咱目前就直接写死是科技平台
        List<科技平台DO> list=new ArrayList<>();

        add_list(map1,list,rs1);

        //根据id2和file2找一下它的全部数据
        PreparedStatement ps2=conn2.prepareStatement(selectSql);
        ResultSet rs2=ps2.executeQuery();
        add_list(map2,list,rs2);
        return list;
    }

    public static MetadataField get_value(Map<Set<MetadataField>,Set<MetadataField>> map,Set<MetadataField> key)
    {
        Set<MetadataField> value=map.get(key);
        for(MetadataField m:value)
        {
            return m;
        }
        return null;
    }

    public static Set<MetadataField> contain_key_nameZH(String name,Map<Set<MetadataField>,Set<MetadataField>> map)
    {
        for(Set<MetadataField> set: map.keySet())
        {
            for(MetadataField m:set)
            {
                if(m.getNameZH().equals(name))
                    return set;
                break;
            }
        }
        return null;
    }

    @Override
    public JsonData syncAdd(int id1, int id2, File file1, File file2, String insertSql)
    {
        SyncAddRequest request=new SyncAddRequest(id1,id2,file1,file2,insertSql);
        EventMessage eventMessage=EventMessage.builder().content(JsonUtil.obj2Json(request)).messageId(CommonUtil.generateUUID()).eventMessageType(EventMessageType.DATA_ADD.name()).build();
        String sql = new String(request.getInsert_sql().getBytes(), StandardCharsets.UTF_8);
        //进行编译，得到表名（元数据对象名），插入元数据项(list_meta)，插入对应元数据项的内容(content)
        CodePointCharStream cs= CharStreams.fromString(sql);
        sqlLikeLexer lexer=new sqlLikeLexer(cs);
        CommonTokenStream tokens=new CommonTokenStream(lexer);
        sqlLikeParser parser=new sqlLikeParser(tokens);
        ParseTree tree= parser.addData();
        MySqlVisitor visitor=new MySqlVisitor();
        visitor.visit(tree);
        rabbitTemplate.convertAndSend(rabbitMQConfig.getDataEventExchange(),rabbitMQConfig.getDataAddRoutingKey(),eventMessage);
        return JsonData.buildSuccess();
    }

    public boolean handleAddData(EventMessage eventMessage) throws IOException, ParserConfigurationException, SAXException, SQLException, ParseException {
        SyncAddRequest request=JsonUtil.json2Obj(eventMessage.getContent(),SyncAddRequest.class);
        String messageType= eventMessage.getEventMessageType();
        String sql = new String(request.getInsert_sql().getBytes(), StandardCharsets.UTF_8);

        String table=MySqlVisitor.table;           //表名的话都是一样的
        List<String> list_meta=MySqlVisitor.list_meta;
        List<List<String>> content=MySqlVisitor.line_content;

        //先根据两个id读到数据源信息
        DatasourcesDO dataSourceDO1 = datasourcesService.detailDataSource(request.getId1());
        DatasourcesDO dataSourceDO2 = datasourcesService.detailDataSource(request.getId2());

        Connection conn1=getConnByDO(dataSourceDO1);
        Connection conn2=getConnByDO(dataSourceDO2);

        //编写sql
        StringBuilder insert_sql=new StringBuilder("insert into "+table+"(");

        //根据xmlName拿到表的元数据map
        Map<Set<MetadataField>,Set<MetadataField>> map1=fileService.readXML(request.getFile1());
        Map<Set<MetadataField>,Set<MetadataField>> map2=fileService.readXML(request.getFile2());

        //根据list_meta找到异构数据源的列名
        List<MetadataField> li_map=new ArrayList<>();
        int lines= list_meta.size();
        for(String line:list_meta)
        {
            //各异构数据库的列名
            if(EventMessageType.DATA_ADD_FIRST.name().equals(messageType))
            {
                Set<MetadataField> set=contain_key_nameZH(line,map1);          //根据名字找到元数据对应的key
                if(line!=null && set!=null)
                {
                    for(MetadataField m:map1.get(set))
                    {
                        //TODO 先随机取出异构的一个元素
                        insert_sql.append(m.getNameZH()).append(",");
                        li_map.add(m);
                        break;
                    }
                }
                else
                {
                    li_map.add(null);
                    lines--;
                }
            }
            else
            {
                Set<MetadataField> set=contain_key_nameZH(line,map2);
                if(line!=null && set!=null)
                {
                    for(MetadataField m:map1.get(set))
                    {
                        //TODO 先随机取出异构的一个元素
                        insert_sql.append(m.getNameZH()).append(",");
                        li_map.add(m);
                        break;
                    }
                }
                else
                {
                    li_map.add(null);
                    lines--;
                }
            }
        }
        //去掉最后一个,
        insert_sql.deleteCharAt(insert_sql.length()-1);
        insert_sql.append(") values(");

        for(int i=0;i< lines;i++)
        {
            if(i+1< lines)
                insert_sql.append("?,");
            else
                insert_sql.append("?");
        }
        insert_sql.append(");");
        PreparedStatement ps=null;
        if(EventMessageType.DATA_ADD_FIRST.name().equals(messageType))
        {
            ps=conn1.prepareStatement(insert_sql.toString());
        }
        else
        {
            ps=conn2.prepareStatement(insert_sql.toString());
        }

        //填充?，即填充具体数据
        int i=0;                              //指向list的索引，从而得到元数据列
        int location=0;                       //指向SQL语句？的位置
        int batch_num=0;
        for(List<String> list:content)        //第一层，每个元素就是一条sql
        {
            for(String s:list)
            {
                //字符串型转为数字型
                if(li_map.get(i)!=null && li_map.get(i).getDataType().equals(MetadataFieldDataType.NUMBER))
                {
                    int o=Integer.parseInt(s);
                    ps.setInt(location+1,o);
                    location++;
                }
                else if(li_map.get(i)!=null && li_map.get(i).getDataType().equals(MetadataFieldDataType.DATETIME))
                {
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    Date o=sdf.parse(s);
                    java.sql.Date d=new java.sql.Date(o.getTime());
                    ps.setDate(location+1, d);
                    location++;
                }
                else if(li_map.get(i)!=null)         //就是字符串
                {
                    ps.setString(location+1,s);
                    location++;
                }
                i++;
            }
            i=0;location=0;
            //有完整sql了
            ps.addBatch();    //打包
            batch_num++;
            if(batch_num>10)
            {
                ps.executeBatch();
                ps.clearBatch();
                batch_num=0;
            }
        }
        ps.executeBatch();
        ps.clearBatch();
        conn1.close();conn2.close();

        //清空一下MySqlVisitor
        return true;
    }
}
