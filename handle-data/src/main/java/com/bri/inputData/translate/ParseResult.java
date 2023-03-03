package com.bri.inputData.translate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseResult {

    /**
     * 字符串转unicode
     *
     * @param str
     * @return
     */
    public static String stringToUnicode(String str) {
        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            sb.append("\\u" + Integer.toHexString(c[i]));
        }
        return sb.toString();
    }

    /**
     * unicode转字符串
     *
     * @param unicode
     * @return
     */
    public static String unicodeToString(String unicode) {
        StringBuffer sb = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            sb.append((char) index);
        }
        return sb.toString();
    }

    public static String parse(String json) throws UnsupportedEncodingException {
        String transResult = getParamByRex(json, "([^\\[]+)\\]");
        String commaRight = getParamByRex(transResult, "([^\\,]+)\\}");
        String colonRight = getParamByRex(commaRight, "([^\\:]+)");
        String dst = colonRight.substring(1, colonRight.length() - 1);
        if (dst.contains("\\u")) {
            return unicodeToString(URLDecoder.decode(dst, "UTF-8"));
        } else {
            return dst;
        }
    }

    /**
     * 返回json字符串中对应的值
     * json json格式字符串
     * regex 正则表达式
     */
    public static String getParamByRex(String json, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        String result = null;
        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

}