package com.bri.webdemo.service;

import com.bri.webdemo.controller.request.AddDataSourceRequest;
import com.bri.webdemo.controller.request.UpdateDataSourceRequest;
import com.bri.webdemo.model.DataSourceDO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bri1987
 * @since 2023-02-27
 */
public interface DataSourceService{
    int createDataSource(AddDataSourceRequest addDataSourceRequest);
    int delDataSource(Long id);

    int updateById(UpdateDataSourceRequest request);

    DataSourceDO detailDataSource(Long id);

    List<DataSourceDO> listAll();
}
