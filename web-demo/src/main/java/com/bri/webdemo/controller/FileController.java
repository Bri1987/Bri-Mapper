package com.bri.webdemo.controller;

import com.bri.webdemo.enums.BizCodeEnum;
import com.bri.webdemo.service.FileService;
import com.bri.webdemo.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "*")
public class FileController
{
    @Autowired
    private FileService fileService;

    @PostMapping("/xml")
    public JsonData uploadXML(@RequestPart("file")MultipartFile file)
    {
        String result = fileService.uploadXML(file);

        //若成功则返回图片地址
        return result!=null ? JsonData.buildSuccess(result):JsonData.buildResult(BizCodeEnum.FILE_UPLOAD_USER_FAIL);
    }
}
