package com.bri.webfinal.controller.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddDataSourceRequest
{
    private String ip;

    private String password;

    private String user;

    //目前暂时用1代表mysql
    private int dtype;

    private String dbname;
}

