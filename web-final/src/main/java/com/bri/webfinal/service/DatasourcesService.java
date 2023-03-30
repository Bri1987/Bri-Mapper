package com.bri.webfinal.service;

import com.bri.webfinal.controller.request.AddDataSourceRequest;
import com.bri.webfinal.controller.request.UpdateDataSourceRequest;
import com.bri.webfinal.model.DatasourcesDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据源 服务类
 * </p>
 *
 * @author Bri1987
 * @since 2023-03-15
 */
public interface DatasourcesService {
    int createDataSource(AddDataSourceRequest addDataSourceRequest) throws Exception;
    int delDataSource(int id);

    int updateById(UpdateDataSourceRequest request);
    DatasourcesDO detailDataSource(int id);
    List<DatasourcesDO> listAll();

    List<DatasourcesDO> listByPage(int page,int size);
}
