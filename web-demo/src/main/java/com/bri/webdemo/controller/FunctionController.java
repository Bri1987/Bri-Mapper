package com.bri.webdemo.controller;

import com.bri.webdemo.controller.request.ExchangeRequest;
import com.bri.webdemo.controller.request.SyncAddRequest;
import com.bri.webdemo.service.FunctionService;
import com.bri.webdemo.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/function")
@CrossOrigin(origins = "*")
public class FunctionController
{
    @Autowired
    private FunctionService functionService;
    @PostMapping("/exchange")
    //数据交换
    public JsonData exchange(@RequestBody ExchangeRequest request)
    {
        try {
            functionService.exchange(request.getId1(), request.getId2(), request.getXmlName1(), request.getXmlName2());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return JsonData.buildSuccess();
    }

    //增量同步
    @PostMapping("/sync/add")
    public JsonData syncAdd(@RequestBody SyncAddRequest request)
    {
        return functionService.syncAdd(request);
    }
}
