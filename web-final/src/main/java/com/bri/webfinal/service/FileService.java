package com.bri.webfinal.service;

import com.bri.inputData.entity.MetadataField;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface FileService {
    String giveFileName(MultipartFile file);
    String uploadXML(MultipartFile file);
    Map<MetadataField,MetadataField> readXML(String objectName) throws IOException, SAXException, ParserConfigurationException;
    File multipartFileToFile(MultipartFile file) throws IOException;
    String getName();
    void download(String file_Id, HttpServletResponse response) throws IOException;
}
