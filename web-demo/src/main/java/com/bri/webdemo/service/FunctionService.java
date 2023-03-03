package com.bri.webdemo.service;

import com.bri.webdemo.controller.request.SyncAddRequest;
import com.bri.webdemo.model.EventMessage;
import com.bri.webdemo.util.JsonData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public interface FunctionService
{
    void exchange(long id1,long id2,String xmlName1,String xmlName2) throws SQLException, IOException, ParserConfigurationException, SAXException;

    JsonData syncAdd(SyncAddRequest request);

    boolean handleAddData(EventMessage eventMessage) throws IOException, ParserConfigurationException, SAXException, SQLException, ParseException;
}
