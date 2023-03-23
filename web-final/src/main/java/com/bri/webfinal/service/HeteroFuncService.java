package com.bri.webfinal.service;

import com.bri.webfinal.model.HeteroTech;
import com.bri.webfinal.model.科技平台DO;
import com.bri.webfinal.util.JsonData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface HeteroFuncService
{
    List<HeteroTech> exchange(int id1, int id2, File file1, File file2, String table_name) throws SQLException, IOException, ParserConfigurationException, SAXException;
    //JsonData syncAdd(int id1, int id2, File file1, File file2, String insertSql);
}
