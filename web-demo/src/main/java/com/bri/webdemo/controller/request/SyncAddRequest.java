package com.bri.webdemo.controller.request;

import lombok.Data;

@Data
public class SyncAddRequest
{
    int id1;
    int id2;

    String xmlName1;
    String xmlName2;

    String insert_sql;
}
