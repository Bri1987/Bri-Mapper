package com.bri.webfinal.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bri.webfinal.enums.BizCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonData {
    /**
     * 状态码 0 表示成功
     */
    private Integer code;
    /**
     * 数据
     */
    private Object data;
    /**
     * 描述
     */
    private String msg;

    /**
     * 获取远程调⽤数据
     * 注意事项：
     * ⽀持多单词下划线专驼峰（序列化和反序列化）
     *
     *
     * parseObject方法是反序列化
     *toJSONString是序列化
     *
     */

    public <T> T getData(TypeReference<T> typeReference){
        return JSON.parseObject(JSON.toJSONString(data),typeReference);
    }

    /**
     * 成功，不传⼊数据
     * @return
     */
    public static JsonData buildSuccess() {
        return new JsonData(0, null, null);
    }

    /**
     * 成功，传⼊数据
     * @param data
     * @return
     */
    public static JsonData buildSuccess(Object data)
    {
        return new JsonData(0, data, null);
    }

    public static JsonData buildSuccess(Object data,int total)
    {
        return new JsonData(0,data,Integer.toString(total));
    }

    /**
     * 失败，传⼊描述信息
     * @param msg
     * @return
     */
    public static JsonData buildError(String msg) {
        return new JsonData(-1, null, msg);
    }

    /**
     * ⾃定义状态码和错误信息
     * @param code
     * @param msg
     * @return
     */
    public static JsonData buildCodeAndMsg(int code,String msg) {
        return new JsonData(code, null, msg);
    }

    /**
     * 传⼊枚举，返回信息
     * @param codeEnum
     * @return
     */
    public static JsonData buildResult(BizCodeEnum codeEnum){
        return JsonData.buildCodeAndMsg(codeEnum.getCode(),codeEnum.getMessage());
    }

}
