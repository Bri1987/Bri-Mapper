package com.bri.webfinal.service;

import cn.ac.amss.semanticweb.alignment.Mapping;
import com.bri.inputData.deal_data.map2xml;
import com.bri.inputData.entity.MetadataField;
import com.bri.webfinal.service.impl.FileServiceImpl;
import com.bri.webfinal.service.impl.SessionServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import static cn.ac.amss.Demo.map_lexi;
import static com.bri.inputData.Main.mapping2map;
import static com.bri.inputData.deal_data.bean2Map.metadata2map;
import static com.bri.inputData.deal_data.csv2Bean.readMetadataField;

@Slf4j
@Data
public class MappingTask implements Runnable
{
    private String sessionId;
    private File file_meta;
    private File file_wrong;

    //文件上传
    private FileServiceImpl impl;
    private String file_name;


    public MappingTask(File file_meta, File file_wrong,FileServiceImpl impl,String sessionId,String file_name){
        this.file_meta=file_meta;
        this.file_wrong=file_wrong;
        this.impl=impl;
        this.sessionId=sessionId;
        this.file_name=file_name;
    }

    public static MultipartFile toMultipartFile(File file) throws IOException {
        // 需要导入commons-fileupload的包
        FileItem fileItem = new DiskFileItem("copyfile.txt", Files.probeContentType(file.toPath()),false,file.getName(),(int)file.length(),file.getParentFile());
        byte[] buffer = new byte[4096];
        int n;
        try (InputStream inputStream = new FileInputStream(file); OutputStream os = fileItem.getOutputStream()){
            while ( (n = inputStream.read(buffer,0,4096)) != -1){
                os.write(buffer,0,n);
            }
            //也可以用IOUtils.copy(inputStream,os);
            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
            return multipartFile;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void run() {
        ArrayList<MetadataField> metadataFields = null;
        try {
            metadataFields = readMetadataField(this.file_meta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<MetadataField> sourceDataFields = null;
        try {
            sourceDataFields = readMetadataField(this.file_wrong);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, Set<MetadataField>> map_meta=metadata2map(metadataFields);
        Map<String,Set<MetadataField>> map_source=metadata2map(sourceDataFields);

        Mapping lexi_map =map_lexi();
        Map<Set<MetadataField>,Set<MetadataField>> final_map= mapping2map(lexi_map,map_meta,map_source);

        //将结果写入xml文件
        File file=new File("./"+this.file_name+".xml");
        map2xml final_xml=new map2xml(final_map);
        try {
            final_xml.writeXML(file);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        //将文件上传到oss
        try {
            impl.uploadXML(toMultipartFile(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
