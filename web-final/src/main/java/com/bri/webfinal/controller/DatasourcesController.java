package com.bri.webfinal.controller;


import com.bri.webfinal.controller.request.AddDataSourceRequest;
import com.bri.webfinal.controller.request.UpdateDataSourceRequest;
import com.bri.webfinal.enums.BizCodeEnum;
import com.bri.webfinal.model.DatasourcesDO;
import com.bri.webfinal.service.DatasourcesService;
import com.bri.webfinal.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 数据源 前端控制器
 * </p>
 *
 * @author Bri1987
 * @since 2023-03-15
 */
@RestController
@RequestMapping("/datasource")
@CrossOrigin(origins = "*")
public class DatasourcesController {
    @Autowired
    private DatasourcesService dataSourceService;

    //新增数据源
    @PostMapping("/add")
    public JsonData createDataSource(@RequestBody AddDataSourceRequest addDataSourceRequest) throws Exception {
        int rows = dataSourceService.createDataSource(addDataSourceRequest);
        return rows==1? JsonData.buildSuccess():JsonData.buildResult(BizCodeEnum.ADD_DATASOURCE_FAIL);
    }

    //删除数据源
    @DeleteMapping("/del/{id}")
    public JsonData deleteDataSource(@PathVariable("id") int id)
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
    public JsonData detailDataSource(@PathVariable("id") int id)
    {
        DatasourcesDO dataSourceDO=dataSourceService.detailDataSource(id);
        return JsonData.buildSuccess(dataSourceDO);
    }

    //列出所有数据源
    @GetMapping("/list")
    public JsonData listAll()
    {
        List<DatasourcesDO> list=dataSourceService.listAll();
        return JsonData.buildSuccess(list);
    }
}

