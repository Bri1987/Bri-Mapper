package com.bri.inputData.translate;

import java.util.LinkedHashMap;
import java.util.Map;

public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;

    public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return HttpGet.get(TRANS_API_HOST, params);
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new LinkedHashMap<>();
        //请求翻译query 	UTF-8编码
        params.put("q", query);
        //翻译源语言
        params.put("from", from);
        //译文语言
        params.put("to", to);
        //APP ID
        params.put("appid", appid);
        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
//        String salt = "1435660288";
        params.put("salt", salt);
        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5Utils.md5(src));

        return params;
    }

}