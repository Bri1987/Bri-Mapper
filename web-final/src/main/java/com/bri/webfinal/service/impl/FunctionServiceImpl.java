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
import com.bri.webfinal.service.DatasourcesService;
import com.bri.webfinal.service.FileService;
import com.bri.webfinal.service.FunctionService;
import com.bri.webfinal.util.CommonUtil;
import com.bri.webfinal.util.JsonData;
import com.bri.webfinal.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
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

    public void data_exchange(ResultSet rs, Connection conn2, String table2, Map<Set<MetadataField>,Set<MetadataField>> map) throws SQLException {
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
    }

    @Override
    @Async("threadPoolTaskExecutor")
    //2转1
    public void exchange(int id1, int id2, File xml1,File xml2,String table_name) throws SQLException, IOException, ParserConfigurationException, SAXException {
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

        data_exchange(rs,conn2,table_name,final_map);
        conn1.close();
        conn2.close();
        log.info("数据交换完毕");
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
