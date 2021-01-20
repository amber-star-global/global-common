package com.global.common.utils.constants.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-03-11 下午 05:37
 * @Version: v1.0
 */
@Slf4j
public class Base64Util {


    /**
     * 数据加密
     * @param data 数据
     */
    public static String encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    /**
     * 数据解密
     * @param data 数据
     */
    public static String decode(String data) {
        return new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8);
    }
}
