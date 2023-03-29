package com.bri.webfinal.controller;

import com.bri.webfinal.model.HeteroTech;
import com.bri.webfinal.service.FileService;
import com.bri.webfinal.service.HeteroFuncService;
import com.bri.webfinal.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/heterogeneous")
public class HeteroFuncController
{
    @Autowired
    private FileService fileService;

    @Autowired
    private HeteroFuncService heteroFuncService;

    @PostMapping("/postgresql/exchange")
    public JsonData mysqlToPostgresql(@RequestParam("id1")int id1, @RequestParam("id2")int id2, @RequestParam("file1")MultipartFile file1,@RequestParam("file2")MultipartFile file2,@RequestParam("table_name")String table_name) throws Exception {
        File file_1=fileService.multipartFileToFile(file1);
        File file_2=fileService.multipartFileToFile(file2);
        List<HeteroTech> list=heteroFuncService.exchange(id1,id2,file_1,file_2,table_name);
        return JsonData.buildSuccess(list);
    }
}
