package com.bri.webfinal.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("科技平台")
public class HeteroTech implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String 科技平台服务最近发布日期;
    private String 科技平台服务资源标识;
    private String 科技平台服务资源名称;
    private Integer 科技平台服务服务方信息;
    private String 科技平台服务内容;
    private String 科技平台服务访问限制;
}
