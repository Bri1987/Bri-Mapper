package com.bri.webfinal.service;

import com.bri.webfinal.model.EventMessage;
import com.bri.webfinal.model.科技平台DO;
import com.bri.webfinal.util.JsonData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface FunctionService {
    JsonData syncAdd(int id1, int id2, File file1, File file2, String insertSql);
    boolean handleAddData(EventMessage eventMessage) throws IOException, ParserConfigurationException, SAXException, SQLException, ParseException;
    void exchange(int id1, int id2, File xml1,File xml2,String table_name) throws SQLException, IOException, ParserConfigurationException, SAXException;

    List<科技平台DO> syncSelect(int id1, int id2, File file1, File file2, String selectSql) throws SQLException, IOException, ParserConfigurationException, SAXException, ParseException;
}
