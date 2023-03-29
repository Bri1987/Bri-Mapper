package com.bri.webfinal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bri.webfinal.controller.request.AddDataSourceRequest;
import com.bri.webfinal.controller.request.UpdateDataSourceRequest;
import com.bri.webfinal.model.DatasourcesDO;
import com.bri.webfinal.mapper.DatasourcesMapper;
import com.bri.webfinal.service.DatasourcesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bri.webfinal.util.SM4Util;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>
 * 数据源 服务实现类
 * </p>
 *
 * @author Bri1987
 * @since 2023-03-15
 */
@Service
public class DatasourcesServiceImpl implements DatasourcesService {
    @Autowired
    private DatasourcesMapper datasourcesMapper;

    @Override
    public int createDataSource(AddDataSourceRequest addDataSourceRequest) throws Exception {
        DatasourcesDO dataSourceDO=new DatasourcesDO();
        dataSourceDO.setUser(addDataSourceRequest.getUser());
        dataSourceDO.setDatasourceType(addDataSourceRequest.getDatasource_type());
        //密码加密
        dataSourceDO.setPassword(SM4Util.encryptSm4(addDataSourceRequest.getPassword()));
        dataSourceDO.setIp(addDataSourceRequest.getIp());
        dataSourceDO.setDbname(addDataSourceRequest.getDbname());
        return datasourcesMapper.insert(dataSourceDO);
    }

    @Override
    public int delDataSource(int id) {
        return datasourcesMapper.delete(new QueryWrapper<DatasourcesDO>().eq("id",id));
    }

    @Override
    public int updateById(UpdateDataSourceRequest request) {
        DatasourcesDO dataSourceDO=new DatasourcesDO();
        dataSourceDO.setUser(request.getUser());
        dataSourceDO.setIp(request.getIp());
        dataSourceDO.setDatasourceType(request.getDatasource_type());
        dataSourceDO.setPassword(request.getPassword());
        dataSourceDO.setDbname(request.getDbname());
        dataSourceDO.setId(request.getId());
        return datasourcesMapper.update(dataSourceDO,new QueryWrapper<DatasourcesDO>().eq("id",dataSourceDO.getId()));
    }

    @Override
    public DatasourcesDO detailDataSource(int id) {
        return datasourcesMapper.selectOne(new QueryWrapper<DatasourcesDO>().eq("id",id));
    }

    @Override
    public List<DatasourcesDO> listAll() {
        return datasourcesMapper.selectList(new QueryWrapper<DatasourcesDO>());
    }
}
