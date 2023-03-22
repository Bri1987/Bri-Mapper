package com.bri.webfinal.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncAddRequest
{
    int id1;
    int id2;
    File file1;
    File file2;
    String insert_sql;
}
