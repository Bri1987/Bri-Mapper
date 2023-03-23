package com.bri.webfinal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bri.webfinal.model.DatasourcesDO;
import com.bri.webfinal.model.科技平台DO;
import com.bri.webfinal.mapper.科技平台Mapper;
import com.bri.webfinal.service.科技平台Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bri1987
 * @since 2023-03-23
 */
@Service
public class 科技平台ServiceImpl implements 科技平台Service {

    @Autowired
    private 科技平台Mapper mapper;
    @Override
    public List<科技平台DO> listAll() {
        return mapper.selectList(new QueryWrapper<科技平台DO>());
    }
}
