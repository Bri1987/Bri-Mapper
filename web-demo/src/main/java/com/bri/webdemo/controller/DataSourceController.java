package com.bri.webdemo.controller;

import com.bri.webdemo.controller.request.AddDataSourceRequest;
import com.bri.webdemo.controller.request.UpdateDataSourceRequest;
import com.bri.webdemo.enums.BizCodeEnum;
import com.bri.webdemo.model.DataSourceDO;
import com.bri.webdemo.service.DataSourceService;
import com.bri.webdemo.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/datasource")
@CrossOrigin(origins = "*")
public class DataSourceController
{
    @Autowired
    private DataSourceService dataSourceService;

    //新增数据源
    @PostMapping("/add")
    public JsonData createDataSource(@RequestBody AddDataSourceRequest addDataSourceRequest)
    {
        int rows = dataSourceService.createDataSource(addDataSourceRequest);
        return rows==1? JsonData.buildSuccess():JsonData.buildResult(BizCodeEnum.ADD_DATASOURCE_FAIL);
    }

    //删除数据源
    @DeleteMapping("/del/{id}")
    public JsonData deleteDataSource(@PathVariable("id") Long id)
    {
        int rows=dataSourceService.delDataSource(id);
        return rows==1? JsonData.buildSuccess():JsonData.buildResult(BizCodeEnum.DEL_DATASOURCE_FAIL);
    }

    //更新数据源
    @PostMapping("/update")
    public JsonData updateDataSource(@RequestBody UpdateDataSourceRequest request)
    {
        int rows=dataSourceService.updateById(request);
        return rows==1? JsonData.buildSuccess():JsonData.buildResult(BizCodeEnum.UPDATE_DATASOURCE_FAIL);
    }

    //根据id查询数据源
    @GetMapping("/detail/{id}")
    public JsonData detailDataSource(@PathVariable("id") Long id)
    {
        DataSourceDO dataSourceDO=dataSourceService.detailDataSource(id);
        return JsonData.buildSuccess(dataSourceDO);
    }

    //列出所有数据源
    @GetMapping("/list")
    public JsonData listAll()
    {
        List<DataSourceDO> list=dataSourceService.listAll();
        return JsonData.buildSuccess(list);
    }
}
