package com.global.common.utils.constants.tools;


import java.util.Collection;
import java.util.Map;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-02 上午 11:06
 * @Version: v1.0
 */
public class VerifyUtil {


    /**
     * 校验任意类型对象不能为空
     * @param object 校验参数
     */
    public static <T> boolean notNull(T object) {
        return object != null;
    }

    /**
     * 校验String类型对象不为空, 且不为""
     * @param string 校验参数
     */
    public static boolean notNull(String string) {
        return string != null && !"".equals(string.trim());
    }

    /**
     * 校验array类型对象不能为空
     * @param object 校验参数
     */
    public static <T> boolean notNull(T[] object) {
        return object != null && object.length > 0;
    }

    /**
     * 校验collection类型对象不能为空, 且元素大小为0
     * @param collection 校验参数
     */
    public static <T> boolean notNull(Collection<T> collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * 校验map类型对象不能为空, 且元素大小为0
     * @param map 校验参数
     */
    public static <K, V> boolean notNull(Map<K, V> map) {
        return map != null && !map.isEmpty();
    }

    /**
     * 校验任意类型对象不能为空
     * @param object 校验参数
     */
    public static <T> boolean isNull(T object) {
        return object == null;
    }

    /**
     * 校验String类型对象不为空, 且不为""
     * @param string 校验参数
     */
    public static boolean isNull(String string) {
        return string == null || "".equals(string.trim());
    }

    /**
     * 校验array类型对象为空
     * @param object 校验参数
     */
    public static <T> boolean isNull(T[] object) {
        return object == null || object.length == 0;
    }

    /**
     * 校验collection类型对象为空, 或者元素大小为0
     * @param collection 校验参数
     */
    public static <T> boolean isNull(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 校验map类型对象为空, 或者元素大小为0
     * @param map 校验参数
     */
    public static <K, V> boolean isNull(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

}