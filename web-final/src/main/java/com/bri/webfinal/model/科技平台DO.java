package com.bri.webfinal.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author Bri1987
 * @since 2023-03-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("科技平台")
public class 科技平台DO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date 科技平台服务最近发布日期;

    private String 科技平台服务资源标识代码;

    private String 科技平台服务资源名称;

    private Integer 科技平台服务服务方信息;

    private String 科技平台服务内容描述;

    private Integer 科技平台服务访问限制;

    private String 科技平台服务关键词;
    private String 科技平台服务名称;
    private String 科技平台服务分类;
}
