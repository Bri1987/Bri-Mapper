package com.bri.webfinal.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateDataSourceRequest
{
    private String ip;

    private String password;

    private String user;

    //目前暂时用1代表mysql
    private int dtype;

    private String dbname;
    private int id;
}

