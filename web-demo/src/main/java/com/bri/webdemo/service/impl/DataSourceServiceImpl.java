package com.bri.webdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bri.webdemo.controller.request.AddDataSourceRequest;
import com.bri.webdemo.controller.request.UpdateDataSourceRequest;
import com.bri.webdemo.model.DataSourceDO;
import com.bri.webdemo.mapper.DataSourceMapper;
import com.bri.webdemo.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bri1987
 * @since 2023-02-27
 */
@Service
public class DataSourceServiceImpl implements DataSourceService{

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Override
    public int createDataSource(AddDataSourceRequest addDataSourceRequest) {
        DataSourceDO dataSourceDO=new DataSourceDO();
        dataSourceDO.setUser(addDataSourceRequest.getUser());
        dataSourceDO.setUrl(addDataSourceRequest.getUrl());
        dataSourceDO.setDatasourceType(addDataSourceRequest.getDatasource_type());
        dataSourceDO.setPassword(addDataSourceRequest.getPassword());
        dataSourceDO.setTableName(addDataSourceRequest.getTable_name());
        return dataSourceMapper.insert(dataSourceDO);
    }

    @Override
    public int delDataSource(Long id) {
        return dataSourceMapper.delete(new QueryWrapper<DataSourceDO>().eq("id",id));
    }

    @Override
    public int updateById(UpdateDataSourceRequest request) {
        DataSourceDO dataSourceDO=new DataSourceDO();
        dataSourceDO.setUser(request.getUser());
        dataSourceDO.setUrl(request.getUrl());
        dataSourceDO.setDatasourceType(request.getDatasource_type());
        dataSourceDO.setPassword(request.getPassword());
        dataSourceDO.setId(request.getId());
        dataSourceDO.setTableName(request.getTable_name());
        return dataSourceMapper.update(dataSourceDO,new QueryWrapper<DataSourceDO>().eq("id",dataSourceDO.getId()));
    }

    @Override
    public DataSourceDO detailDataSource(Long id) {
        return dataSourceMapper.selectOne(new QueryWrapper<DataSourceDO>().eq("id",id));
    }
}
