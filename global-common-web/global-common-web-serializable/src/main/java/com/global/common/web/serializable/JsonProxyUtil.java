package com.global.common.web.serializable;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * json代理工具类
 * 统一json处理方式, 之后如果修改序列化及反序列化方式不用修改业务代码
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2021-05-18 上午 10:42
 * @Version: v1.0
 */
public class JsonProxyUtil {

    /**
     * 解析json文本
     *
     * @param jsonText json字符串
     */
    public static Object parse(final String jsonText) {
        return JSON.parse(jsonText);
    }

    /**
     * 解析json文本转换成泛型对象
     *
     * @param jsonText json字符串
     * @param clazz    泛型class
     */
    public static <T> T parseObject(final String jsonText, Class<T> clazz) {
        return JSON.parseObject(jsonText, clazz);
    }

    /**
     * 解析json文本转换成泛型对象
     *
     * @param jsonText json字符串
     * @param clazz    泛型class
     */
    public static <T> List<T> parseArray(final String jsonText, Class<T> clazz) {
        return JSON.parseArray(jsonText, clazz);
    }

    /**
     * 转换对象成JsonString
     */
    public static String toJsonString(final Object object) {
        return JSON.toJSONString(object);
    }
}
