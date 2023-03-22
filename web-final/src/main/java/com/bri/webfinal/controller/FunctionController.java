package com.bri.webfinal.controller;

import com.bri.webfinal.service.FileService;
import com.bri.webfinal.service.FunctionService;
import com.bri.webfinal.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/function")
public class FunctionController
{
    @Autowired
    private FileService fileService;

    @Autowired
    private FunctionService functionService;

    //增量同步
    @PostMapping("/sync/add")
    public JsonData syncAdd(@RequestParam("id1") int id1, @RequestParam("id2")int id2, @RequestParam("file1") MultipartFile file1, @RequestParam("file2")MultipartFile file2, @RequestParam("insert_sql")String insert_sql) throws IOException {
        File file_1=fileService.multipartFileToFile(file1);
        File file_2=fileService.multipartFileToFile(file2);
        return functionService.syncAdd(id1,id2,file_1,file_2,insert_sql);
    }

    @PostMapping("/exchange")
    //数据交换
    public JsonData exchange(@RequestParam("id1") int id1,@RequestParam("id2") int id2,@RequestParam("file1")MultipartFile file1,@RequestParam("file2")MultipartFile file2,@RequestParam("table_name")String table_name) throws IOException {
        File file_1=fileService.multipartFileToFile(file1);
        File file_2=fileService.multipartFileToFile(file2);
        try {
            functionService.exchange(id1, id2, file_1, file_2,table_name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return JsonData.buildSuccess();
    }
}
