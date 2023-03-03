package com.bri.webdemo.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateDataSourceRequest
{
    private String url;

    private String password;

    private String user;

    //目前暂时用1代表mysql
    private int datasource_type;

    private int id;

    private String table_name;
}
