package com.bri.webfinal.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.bri.inputData.entity.MetadataField;
import com.bri.inputData.entity.MetadataFieldDataType;
import com.bri.webfinal.config.OSSConfig;
import com.bri.webfinal.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService
{
    @Autowired
    private OSSConfig ossConfig;

    public String giveFileName(MultipartFile file)
    {
        //获取原⽣⽂件名 xxx.xml
        String originalFileName = file.getOriginalFilename();

        String fileName = originalFileName.split("\\.")[0];
        return fileName;
    }

    public File multipartFileToFile(MultipartFile file) throws IOException {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

//    @Override
//    public String getName() {
//        return UUID.randomUUID().toString();
//    }

    @Override
    public void download(String file_Id, HttpServletResponse response) throws IOException {
//        //获取相关配置
//        String bucketname =ossConfig.getBucketname();
//        String endpoint = ossConfig.getEndpoint();
//        String accessKeyId =ossConfig.getAccessKeyId();
//        String accessKeySecret =ossConfig.getAccessKeySecret();

        String bucketname = "a39";
        String endpoint = "oss-cn-chengdu.aliyuncs.com";
        String accessKeyId = "LTAI5tHwfZJfbPs633xJH148";
        String accessKeySecret = "3Snf2eIasYpN6g45RL5X1FR7yf1H58";

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String objectName="a39/"+file_Id+".xml";
        OSSObject ossObject = ossClient.getObject(bucketname, objectName);

        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-msdownload");
        try {
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(objectName.getBytes("gb2312"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        BufferedInputStream input=new BufferedInputStream(ossObject.getObjectContent());
        byte[] buffer=new byte[1024];
        int read=0;
        OutputStream outputStream=response.getOutputStream();

        while ((read=input.read(buffer))!=-1){
            outputStream.write(buffer,0,read);
        }
        outputStream.flush();
        ossObject.close();
        outputStream.close();
        input.close();
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String uploadXML(MultipartFile file) {
//        //获取相关配置
//        String bucketname = ossConfig.getBucketname();
//        String endpoint = ossConfig.getEndpoint();
//        String accessKeyId = ossConfig.getAccessKeyId();
//        String accessKeySecret = ossConfig.getAccessKeySecret();

        String bucketname = "a39";
        String endpoint = "oss-cn-chengdu.aliyuncs.com";
        String accessKeyId = "LTAI5tHwfZJfbPs633xJH148";
        String accessKeySecret = "3Snf2eIasYpN6g45RL5X1FR7yf1H58";

        //创建OSS对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //获取原⽣⽂件名 xxx.xml
        String originalFileName = file.getOriginalFilename();

        String fileName = originalFileName.split("\\.")[0];
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 在OSS上的bucket下创建 a39 这个⽂件夹
        String newFileName = "a39/" + fileName + extension;

        try {
            PutObjectResult putObjectResult = ossClient.putObject(bucketname, newFileName, file.getInputStream());
            System.out.println("hhhhhhhhhhhhhhhh");
            //拼装返回路径
            if (putObjectResult != null) {
                String xmlUrl = "https://" + bucketname + "." + endpoint + "/" + newFileName;
                return xmlUrl;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //oss关闭服务，不然会造成OOM
            ossClient.shutdown();
        }
        return null;
    }

    public static MetadataField setValue(Node value, Attr attr, MetadataField field1)
    {
        if(field1==null)
            field1=new MetadataField();
        String content= value.getTextContent();

        if(attr.getValue().equals("中文名称")) {
            field1.setNameZH(content);
        }
        //TODO 数据类型
        else if(attr.getValue().equals("数据类型"))
        {
            if(content.equals("TEXT"))
                field1.setDataType(MetadataFieldDataType.TEXT);
            else if(content.equals("NUMBER"))
                field1.setDataType(MetadataFieldDataType.NUMBER);
            else if(content.equals("DATETIME"))
                field1.setDataType(MetadataFieldDataType.DATETIME);
            else if(content.equals("BINARY"))
                field1.setDataType(MetadataFieldDataType.BINARY);
            else if(content.equals("BOOLEAN"))
                field1.setDataType(MetadataFieldDataType.BOOLEAN);
        }
        else if(attr.getValue().equals("类词"))
            field1.setFieldType(content);
        else if(attr.getValue().equals("计量单位"))
            field1.setUnit(content);
        else if(attr.getValue().equals("定义"))
            field1.setDescription(content);
        else if(attr.getValue().equals("参考来源"))
            field1.setReference(content);
        else if(attr.getValue().equals("参考来源MJ"))
            field1.setReferenceMJ(content);
        else if(attr.getValue().equals("描述对象"))
            field1.setObject(content);
        else if(attr.getValue().equals("所属分类"))
            field1.setCategory(content);
        else if(attr.getValue().equals("长度")) {
            NodeList length_list=value.getChildNodes();
            for(int tmp=0;tmp<length_list.getLength();tmp++)
            {
                Node min_max=length_list.item(tmp);
                if(min_max.getNodeName().equals("min"))
                    field1.setMinLength(Integer.parseInt(min_max.getTextContent()));
                else if(min_max.getNodeName().equals("max"))
                    field1.setMaxLength(Integer.parseInt(min_max.getTextContent()));
            }
        }
        return field1;
    }
    //进来的是root节点
    public static Map<MetadataField, MetadataField> parseElement(Element element)
    {
        Map<MetadataField,MetadataField> map=new HashMap<>();

        //节点
        //是所有map节点
        NodeList nodeList=element.getChildNodes();
        Node childNode;
        MetadataField field1=null,field2=null;
        for(int i=0;i<nodeList.getLength();i++)
        {
            childNode=nodeList.item(i);              //childNode是每一个map节点
            NodeList map_list=childNode.getChildNodes();
            //再走一层，是meta或source
            for(int k=0;k<map_list.getLength();k++)
            {
                Node map_child=map_list.item(k);
                //meta
                if(map_child.getNodeName().equals("meta"))
                {
                    NodeList meta_list=map_child.getChildNodes();
                    //是各个属性了
                    for(int j=0;j<meta_list.getLength();j++)
                    {
                        Node value=meta_list.item(j);
                        NamedNodeMap attris = value.getAttributes();
                        if(attris!=null)
                        {
                            for (int o = 0; o < attris.getLength(); o++) {
                                //只会有一个attr
                                Attr attr = (Attr) attris.item(o);
                                if(attr!=null)
                                    field1=setValue(value,attr,field1);
                            }
                        }
                    }
                }
                //source
                else if(map_child.getNodeName().equals("source"))
                {
                    NodeList source_list=map_child.getChildNodes();
                    //是各个属性了
                    for(int j=0;j<source_list.getLength();j++)
                    {
                        Node value=source_list.item(j);
                        NamedNodeMap attris = value.getAttributes();
                        if(attris!=null)
                        {
                            for (int o = 0; o < attris.getLength(); o++) {
                                //只会有一个attr
                                Attr attr = (Attr) attris.item(o);
                                if(attr!=null)
                                    field2=setValue(value,attr,field2);
                            }
                        }
                    }
                }
            }
            if(field1!=null && field2!=null)
            {
                //加入map
                map.put(field1,field2);
                field1=null;
                field2=null;
            }
        }
        return map;
    }

    public Map<MetadataField,MetadataField> readXML(String objectName) {
        Map<MetadataField,MetadataField> map=new HashMap<>();

        //获取相关配置
        String bucketname =ossConfig.getBucketname();
        String endpoint = ossConfig.getEndpoint();
        String accessKeyId =ossConfig.getAccessKeyId();
        String accessKeySecret =ossConfig.getAccessKeySecret();

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketname, objectName);
        InputStream content = ossObject.getObjectContent();

        // 初始化一个XML解析工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 创建一个DocumentBuilder实例
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.info("创建DocumentBuilder实例失败");
        }

        // 创建一个解析XML的Document实例
        Document doc = null;
        try {
            doc = builder.parse(content);
        } catch (Exception e) {
            log.info("创建Document实例失败");
        }

        //递归解析element
        //第一个节点,是root节点
        Element element = doc.getDocumentElement();

        map=parseElement(element);

        // 关闭OSSClient。
        ossClient.shutdown();
        return map;
    }
}
