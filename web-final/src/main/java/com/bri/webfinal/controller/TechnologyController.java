package com.bri.webfinal.controller;

import com.bri.webfinal.controller.request.AddDataSourceRequest;
import com.bri.webfinal.controller.request.UpdateDataSourceRequest;
import com.bri.webfinal.enums.BizCodeEnum;
import com.bri.webfinal.model.DatasourcesDO;
import com.bri.webfinal.model.科技平台DO;
import com.bri.webfinal.service.DatasourcesService;
import com.bri.webfinal.service.科技平台Service;
import com.bri.webfinal.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tech")
public class TechnologyController
{
    @Autowired
    private 科技平台Service service;


    //列出所有
    @GetMapping("/list")
    public JsonData listAll()
    {
        List<科技平台DO> list=service.listAll();
        return JsonData.buildSuccess(list);
    }
}
