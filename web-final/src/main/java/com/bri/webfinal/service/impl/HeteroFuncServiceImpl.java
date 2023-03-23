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
import com.bri.webfinal.service.DatasourcesService;
import com.bri.webfinal.service.FileService;
import com.bri.webfinal.service.HeteroFuncService;
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
public class HeteroFuncServiceImpl implements HeteroFuncService
{
    @Autowired
    private DatasourcesService datasourcesService;
    @Autowired
    private FileService fileService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    //连接数据库mysql
    public Connection getConn_mysql(String url, String user, String password) {
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

    //连接数据库mysql
    public Connection getConn_postgresql(String url, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.info("数据源驱动失败postgresql");
        }
        Connection conn= null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            log.info("数据库连接失败postgresql");
        }
        return conn;
    }

    public Connection getConnByDO_mysql(DatasourcesDO d)
    {
        String ip=d.getIp();
        String db=d.getDbname();
        String user1 = d.getUser();
        String password1=d.getPassword();
        String url1="jdbc:mysql://"+ip+":3306/"+db+"?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        return getConn_mysql(url1,user1,password1);
    }

    public Connection getConnByDO_postgresql(DatasourcesDO d)
    {
        String ip=d.getIp();
        String db=d.getDbname();
        String user1 = d.getUser();
        String password1=d.getPassword();
        String url1="jdbc:postgresql://"+ip+":5432/"+db;
        return getConn_postgresql(url1,user1,password1);
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
        StringBuilder sql= new StringBuilder("INSERT INTO " + table2 + "(");
        for(MetadataField source:set_source)
        {
            //得到每一个元数据列
            sql.append(source.getNameZH());
            sql.append(",");
            queue.add(source);
        }
        //去掉最后一个,
        sql.deleteCharAt(sql.length()-1);
        sql.append(") VALUES(");
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
   // @Async("threadPoolTaskExecutor")
    //2转1
    public List<HeteroTech> exchange(int id1, int id2, File file1, File file2, String table_name) throws SQLException, IOException, ParserConfigurationException, SAXException {
        //先根据两个id读到数据源信息
        DatasourcesDO dataSourceDO1 = datasourcesService.detailDataSource(id1);
        DatasourcesDO dataSourceDO2 = datasourcesService.detailDataSource(id2);

        //根据数据源信息连接数据库
        Connection conn1=getConnByDO_mysql(dataSourceDO1);
        Connection conn2=getConnByDO_postgresql(dataSourceDO2);

        //拿到conn1的结果集
        String select = "select * from "+table_name;
        Statement statement=conn1.createStatement();
        ResultSet rs = statement.executeQuery(select);

        //根据xmlName拿到表的元数据map
        Map<Set<MetadataField>,Set<MetadataField>> map1=fileService.readXML(file1);
        Map<Set<MetadataField>,Set<MetadataField>> map2=fileService.readXML(file2);
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

}
