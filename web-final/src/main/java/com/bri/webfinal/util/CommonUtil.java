package com.bri.webfinal.util;

//import com.google.common.hash.Hashing;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.*;

@Slf4j
public class CommonUtil {
    /**
     * 获取ip
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress =request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据⽹卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet =InetAddress.getLocalHost();
                    }
                    catch (UnknownHostException e)
                    {
                        log.error("不知道主机异常:{}",e);
                    }
                    ipAddress =inet.getHostAddress();
                }
            }

            // 对于通过多个代理的情况，第⼀个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress =
                            ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        }
        catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    /**
     * 获取全部请求头
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestHeader(HttpServletRequest request){
        Enumeration<String> headerNames =request.getHeaderNames();
        Map<String, String> map = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String key =(String)headerNames.nextElement();
            //根据名称获取请求头的值
            String value = request.getHeader(key);
            map.put(key,value);
        }
        return map;
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     */
    public static String MD5(String data) {
        try {
            MessageDigest md =MessageDigest.getInstance("MD5");
            byte[] array =md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }

            return sb.toString().toUpperCase();
        }
        catch (Exception exception)
        {
            log.error("加密时发生异常:{}",exception);
        }
        return null;
    }

    /**
     * 获取验证码随机数
     *
     * @param length
     * @return
     */
    public static String getRandomCode(int length) {
        String sources = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < length; j++) {

            sb.append(sources.charAt(random.nextInt(9)));
        }
        return sb.toString();
    }
    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * ⽣成uuid
     *
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-","").substring(0, 32);
    }

    /**
     * 获取随机⻓度的串
     *
     * @param length
     * @return
     */
    private static final String ALL_CHAR_NUM ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static String getStringNumRandom(int length) {
        //⽣成随机数字和字⺟,
        Random random = new Random();
        StringBuilder saltString = new StringBuilder(length);
        for (int i = 1; i <= length; ++i) {
            saltString.append(ALL_CHAR_NUM.charAt(random.nextInt(ALL_CHAR_NUM.length())));
        }
        return saltString.toString();
    }

    /**
     * 响应json数据给前端
     *
     * @param response
     * @param obj
     */
    public static void sendJsonMessage(HttpServletResponse response, Object obj) {
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter writer =response.getWriter()) {
            writer.print(JsonUtil.obj2Json(obj));
            response.flushBuffer();
        }
        catch (IOException e) {
            log.warn("响应json数据给前端异常:{}",e);
        }
    }

    /**
     * murmur hash算法
     * @param param
     * @return
     */
    /*
    public static long murmurHash32(String param)
    {
        long murmur32 = Hashing.murmur3_32().hashUnencodedChars(param).padToLong();
        return murmur32;
    }*/

    /**
     * URL增加前缀
     * @param url
     * @return
     */
    /*
    public static String addUrlPrefix(String url){
        return IdUtil.geneSnowFlakeID()+"&"+url;
    }*/

    /**
     * URL移除前缀
     * @param url
     * @return
     */
    public static String removeUrlPrefix(String url)
    {
        String originalUrl =url.substring(url.indexOf("&")+1);
        return originalUrl;
    }
    /**
     * 如果短链码重复，则调⽤这个⽅法
     * url前缀编号递增1，如果还是⽤雪花算法，则容易C和B端不
     ⼀致，所以采⽤原先的id递增1
     * @param url
     * @return
     */
    public static String addUrlPrefixVersion(String url){
        String result =url.substring(0,url.indexOf("&"));
        //原始地址
        String originalUrl =url.substring(url.indexOf("&")+1);
        //新id编号
        Long newIdValue = Long.parseLong(result)+1;
        return newIdValue+"&"+originalUrl;
    }

    public static void sendHtmlMessage(HttpServletResponse response, JsonData jsonData)
    {
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter writer =response.getWriter()) {
            writer.write(jsonData.toString());
            writer.flush();
        }
        catch (IOException e) {
            log.warn("响应json数据给前端异常:{}",e);
        }
    }
}
