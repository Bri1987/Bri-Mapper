package com.bri.webfinal.controller;

import com.bri.webfinal.controller.request.MappingRequest;
import com.bri.webfinal.service.MappingService;
import com.bri.webfinal.service.MappingTask;
import com.bri.webfinal.service.SessionService;
import com.bri.webfinal.service.WebSocketClientSyncCallback;
import com.bri.webfinal.service.impl.FileServiceImpl;
import com.bri.webfinal.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/mapping")
@RestController
@CrossOrigin(origins = "*")
public class MappingController
{
    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/map")
    public JsonData mapToXML(@RequestParam("sessionId") String sessionId,@RequestParam("file")MultipartFile file,@RequestParam("file_lists")List<MultipartFile> file_lists) throws Exception {

        FileServiceImpl impl=new FileServiceImpl();

        Map<String,String> map=new HashMap<>();
        for(MultipartFile f:file_lists)
        {
            //1.分一个xml文件名字
            String fileId=fileService.giveFileName(f);
            //2.转换并上传到oss
            mappingService.submit(new MappingTask(fileService.multipartFileToFile(file), fileService.multipartFileToFile(f), impl, sessionId, fileId),
                    () -> sessionService.notify_message(sessionId,fileId));

            map.put(fileId,fileId);        //文件id(result)与文件名(w88:w88)
        }
        return JsonData.buildSuccess(map);
    }

    @GetMapping("/download/{fileId}")
    public JsonData download(@PathVariable("fileId") String fileId,HttpServletResponse response) throws IOException {
        fileService.download(fileId,response);
        return JsonData.buildSuccess();
    }
}
