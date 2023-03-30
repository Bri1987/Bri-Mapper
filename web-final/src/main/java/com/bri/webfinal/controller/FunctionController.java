package com.bri.webfinal.controller;

import com.bri.webfinal.model.HeteroTech;
import com.bri.webfinal.model.Metadata;
import com.bri.webfinal.model.科技平台DO;
import com.bri.webfinal.service.ElasticSearchService;
import com.bri.webfinal.service.FileService;
import com.bri.webfinal.service.FunctionService;
import com.bri.webfinal.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/function")
public class FunctionController
{
    @Autowired
    private FileService fileService;

    @Autowired
    private FunctionService functionService;

    @Autowired
    private ElasticSearchService elasticSearchService;

    //增量同步
    @PostMapping("/sync/add")
    public JsonData syncAdd(@RequestParam("id1") int id1, @RequestParam("id2")int id2, @RequestParam("file1") MultipartFile file1, @RequestParam("file2")MultipartFile file2, @RequestParam("insert_sql")String insert_sql) throws IOException {
        File file_1=fileService.multipartFileToFile(file1);
        File file_2=fileService.multipartFileToFile(file2);
        return functionService.syncAdd(id1,id2,file_1,file_2,insert_sql);
    }

    @PostMapping("/exchange")
    //数据交换
    public JsonData exchange(@RequestParam("id1") int id1,@RequestParam("id2") int id2,@RequestParam("file1")MultipartFile file1,@RequestParam("file2")MultipartFile file2,@RequestParam("table_name")String table_name) throws Exception {
        File file_1=fileService.multipartFileToFile(file1);
        File file_2=fileService.multipartFileToFile(file2);
        List<HeteroTech> list=functionService.exchange(id1, id2, file_1, file_2,table_name);
        return JsonData.buildSuccess(list);
    }

    @PostMapping("/sync/select")
    public JsonData syncSelect(@RequestParam("id1")int id1,@RequestParam("id2")int id2,@RequestParam("file1")MultipartFile file1,@RequestParam("file2")MultipartFile file2,@RequestParam("select_sql")String select_sql) throws Exception {
        File file_1=fileService.multipartFileToFile(file1);
        File file_2=fileService.multipartFileToFile(file2);
        List<科技平台DO> list=functionService.syncSelect(id1,id2,file_1,file_2,select_sql);
        return JsonData.buildSuccess(list);
    }

    @PostMapping("/import")
    public JsonData importAll(@RequestParam("id1")int id1,@RequestParam("id2")int id2,@RequestParam("file1")MultipartFile file1,@RequestParam("file2")MultipartFile file2) throws Exception {
        File file_1=fileService.multipartFileToFile(file1);
        File file_2=fileService.multipartFileToFile(file2);
        elasticSearchService.importAll(id1,id2,file_1,file_2);
        return JsonData.buildSuccess();
    }

    @PostMapping("/select")
    public JsonData searchAll(@RequestParam("select_sql")String select_sql) throws IOException {
        List<科技平台DO> list=elasticSearchService.searchAll();
        return JsonData.buildSuccess(list);
    }

    @GetMapping("/delete")
    public JsonData delete() throws IOException {
        elasticSearchService.deleteAllTech();
        return JsonData.buildSuccess();
    }

    @PostMapping("/visualize")
    public JsonData visualize(@RequestParam("file")MultipartFile file) throws IOException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException {
        File file_1=fileService.multipartFileToFile(file);
        Metadata metadata=functionService.visualize(file_1);
        functionService.visualize2(metadata);
        return JsonData.buildSuccess(metadata);
    }
}
