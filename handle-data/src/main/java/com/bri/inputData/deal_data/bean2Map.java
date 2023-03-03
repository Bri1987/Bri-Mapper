package com.bri.inputData.deal_data;

import com.bri.inputData.entity.MetadataField;
import com.bri.inputData.translate.ParseResult;
import com.bri.inputData.translate.TransApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class bean2Map
{
    private static final String APP_ID = "20230211001557557";
    private static final String SECURITY_KEY = "AH2maRs1ZpNspudnmXrG";

    public static Map<String, MetadataField> metadata2map(ArrayList<MetadataField> datasets) {
        //以英文名为key
        Map<String,MetadataField> map=new HashMap<>();
        for(MetadataField dataset:datasets)
        {
            //将英文名中的空格全部替换为_
            String onto_name=dataset.getNameEN().replaceAll(" ","_");

            //加进map
            map.put(onto_name,dataset);
        }
        return map;
    }

    public static Map<String,MetadataField> trans_metadata(ArrayList<MetadataField> datasets) throws IOException {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String query;
        String transResult;
        String parse;
        //加入一个英文名-对象的map中
        Map<String,MetadataField> map=new HashMap<>();

        for(MetadataField dataset:datasets)
        {
            query=dataset.getNameZH();
            transResult = api.getTransResult(query, "zh", "en");
            parse = ParseResult.parse(transResult);
            //确定英文名
            dataset.setNameEN(parse);

            //将英文名中的空格全部替换为_
            String onto_name=parse.replaceAll(" ","_");

            //加进map
            map.put(onto_name,dataset);
        }
        return map;
    }
}
