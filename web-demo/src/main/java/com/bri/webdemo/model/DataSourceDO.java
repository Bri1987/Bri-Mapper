package com.bri.webdemo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author Bri1987
 * @since 2023-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("data_source")
public class DataSourceDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;

    private String password;

    private String user;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer datasourceType;

    private String tableName;
}
