package com.global.common.utils.constants.utils;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 对象校验代理类
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-02 上午 11:06
 * @Version: v1.0
 * @see java.util.Objects
 * @see org.apache.commons.lang3.StringUtils
 * @see org.apache.commons.lang3.ArrayUtils
 * @see org.apache.commons.collections.CollectionUtils
 * @see org.apache.commons.collections.MapUtils
 */
public class VerifyProxyUtil {


    /**
     * 校验任意类型对象不为空
     *
     * @param object 校验参数
     */
    public static boolean nonNull(Object object) {
        return Objects.nonNull(object);
    }

    /**
     * 校验任意类型对象为空
     *
     * @param object 校验参数
     */
    public static boolean isNull(Object object) {
        return Objects.isNull(object);
    }

    /**
     * 校验String类型对象不为空, 且不为""
     *
     * @param text 校验参数
     */
    public static boolean isNotBlank(String text) {
        return StringUtils.isNotBlank(text);
    }

    /**
     * 校验String类型对象为空或为""
     *
     * @param text 校验参数
     */
    public static boolean isBlank(String text) {
        return StringUtils.isBlank(text);
    }

    /**
     * 校验array类型对象不为空
     *
     * @param object 校验参数
     */
    public static boolean isNotEmpty(Object[] object) {
        return ArrayUtils.isNotEmpty(object);
    }

    /**
     * 校验array类型对象为空
     *
     * @param object 校验参数
     */
    public static boolean isEmpty(Object[] object) {
        return ArrayUtils.isEmpty(object);
    }

    /**
     * 校验collection类型对象不为空
     *
     * @param collection 校验参数
     */
    public static boolean isNotEmpty(Collection collection) {
        return CollectionUtils.isNotEmpty(collection);
    }

    /**
     * 校验collection类型对象为空
     *
     * @param collection 校验参数
     */
    public static boolean isEmpty(Collection collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 校验map类型对象不为空
     *
     * @param map 校验参数
     */
    public static boolean isNotEmpty(Map map) {
        return MapUtils.isNotEmpty(map);
    }

    /**
     * 校验map类型对象为空
     *
     * @param map 校验参数
     */
    public static boolean isEmpty(Map map) {
        return MapUtils.isEmpty(map);
    }

}