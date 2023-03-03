package com.bri.webdemo.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRequest
{
    int id1;
    int id2;

    String xmlName1;
    String xmlName2;
}
