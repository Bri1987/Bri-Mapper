package com.bri.webdemo.service;

import com.bri.inputData.entity.MetadataField;
import com.bri.webdemo.util.JsonData;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

public interface FileService
{
    String uploadXML(MultipartFile file);
    Map<MetadataField,MetadataField> readXML(String objectName) throws IOException, SAXException, ParserConfigurationException ;
}
