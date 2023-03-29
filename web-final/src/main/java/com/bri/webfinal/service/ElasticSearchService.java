package com.bri.webfinal.service;

import com.bri.webfinal.model.科技平台DO;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ElasticSearchService
{
    boolean addTech(科技平台DO tech,String id) throws IOException;
    Map<String,Object> getTech(String id) throws IOException;
    boolean deleteTech(String id) throws IOException;
    boolean deleteAllTech() throws IOException;
    boolean importAll(int id1, int id2, File file1, File file2) throws Exception;
    List<科技平台DO> searchAll() throws IOException;
}
