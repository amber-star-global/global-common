package com.global.common.utils.constants.utils.copy;

import com.global.common.utils.constants.utils.VerifyProxyUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-02 上午 11:10
 * @Version: v1.0
 */
@Slf4j
public class ReflectUtil {


    /**
     * 获取全部字段
     * @param object 任意对象
     * @return 对象及父类所有字段
     */
    public static <T> Field[] getAllFields(final T object) {
        Class<?> objectClass = object.getClass();
        Field[] currentFields = {};
        while (objectClass != Object.class) {
            final Field[] fields = objectClass.getDeclaredFields();
            currentFields = CopyArrayUtil.arrayMerge(fields, currentFields);
            objectClass = objectClass.getSuperclass();
        }
        return currentFields;
    }

    /**
     * 设置对字段允许操作处理
     * @param field 需处理的字段
     */
    private static void setAccessible(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * 获取字段值
     * @param object 需要获取字段值的对象
     * @param field 获取值的字段
     */
    public static <T> Object getFieldValue(T object, Field field) {
        try {
            setAccessible(field);
            return field.get(object);
        } catch (IllegalAccessException e) {
            log.error("获取字段值异常!", e);
            return null;
        }
    }

    /**
     * 字段赋值
     * @param object 需要赋值的对象
     * @param field 赋值字段
     * @param value 设置值
     */
    public static <T> void setFiledValue(T object, Field field, Object value) {
        try {
            setAccessible(field);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            log.error("字段赋值异常!", e);
        }
    }

    /**
     * 获取集合类型泛型
     * @param field 需要处理的字段
     */
    public static <T> Class getCollectionGeneric(T object, Field field) {
        Object value = getFieldValue(object, field);
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            if (value instanceof Collection) {
                ParameterizedType pt = (ParameterizedType) genericType;
                return (Class)pt.getActualTypeArguments()[0];
            }
        }
        return null;
    }

    /**
     * 获取对象的属性名称及属性值
     * @param object
     * @return
     */
    public static <T> Map<String, Object> getFieldKeyValue(T object) {
        if (VerifyProxyUtil.isNull(object)) {
            return null;
        }
        Field[] fields = getAllFields(object);
        Map<String, Object> map = new ConcurrentHashMap<>();
        for (Field field : fields) {
            String key = field.getName();
            if ("serialVersionUID".equals(key)) {
                continue;
            }
            Object value = getFieldValue(object, field);
            if (VerifyProxyUtil.nonNull(value)) {
                map.put(key, value);
            }
        }
        return map;
    }
}
