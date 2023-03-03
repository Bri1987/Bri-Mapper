package com.bri.inputData.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 数据元字段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetadataField {
    // 中文名称
    private String nameZH;
    // 同义名称
    private String synonym;
    // 英文名称
    private String nameEN;
    // 表达符号
    private String symbol;
    // 数据类型
    private MetadataFieldDataType dataType;
    // 类词
    private String fieldType;
    // 计量单位
    private String unit;
    // 定义
    private String description;
    // 参考来源
    private String reference;
    // 参考来源MJ
    private String referenceMJ;
    // 描述对象
    private String object;
    // 所属分类
    private String category;
    // 最大长度
    private int minLength;
    // 最小长度
    private int maxLength;
    // 日期格式
    private String format;
    // 最小值
    private BigDecimal minValue;
    // 最大值
    private BigDecimal maxValue;

}

