package com.global.common.utils.constants.utils.copy;

import com.global.common.utils.constants.utils.copy.annotation.AsField;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-13 上午 10:36
 * @Version: v1.0
 */
@Slf4j
public class CopyUtil {

    /**
     * 复制对象
     * @param source 源对象
     * @param targetClass 目标类
     */
    public static <S, T> T copy(S source, Class<T> targetClass) {
        try {
            T target = targetClass.newInstance();
            copy(source, target);
            return target;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 赋值对象
     * @param source 源对象
     * @param target 目标对象
     */
    public static <S, T> void copy(S source, T target) {
        Objects.requireNonNull(source, "源对象为空");
        Objects.requireNonNull(target, "目标对象为空");
        Field[] sourceFields = ReflectUtil.getAllFields(source);
        Field[] targetFields = ReflectUtil.getAllFields(target);
        for (Field targetField : targetFields) {
            // 获取字段名称
            AsField asField = targetField.getAnnotation(AsField.class);
            String fieldName = Objects.nonNull(asField) ? asField.value() : targetField.getName();
            // 获取字段类型
            Class<?> type = targetField.getType();
            Field sourceField = getField(sourceFields, fieldName);
            if (Objects.nonNull(sourceField)) {
                // 判断类型是否一致
                if (type.equals(sourceField.getType())) {
                    // 获取字段的值
                    Object sourceValue = ReflectUtil.getFieldValue(source, sourceField);
                    // 设置字段
                    ReflectUtil.setFiledValue(target, targetField, sourceValue);
                } else if (isCollection(sourceField.getType(), targetField.getType())) {
                    Object sourceValue = ReflectUtil.getFieldValue(source, sourceField);
                    Object targetValue = ReflectUtil.getFieldValue(target, targetField);
                    copy(sourceValue, targetValue);
                }
            }
        }
    }

    /**
     * 获取字段
     * @param sourceFields 源字段
     * @param targetFieldName 目标字段名称
     */
    private static Field getField(Field[] sourceFields, String targetFieldName) {
        for (Field sourceField : sourceFields) {
            if (targetFieldName.equals(sourceField.getName())) {
                return sourceField;
            }
        }
        return null;
    }

    /**
     * 判断源对象和目标对象同为Collection类型
     * @param sourceType 源对象类型
     * @param targetType 目标对象类型
     */
    private static <S, T> boolean isCollection(Class<S> sourceType, Class<T> targetType) {
        return Collection.class.isAssignableFrom(sourceType) && Collection.class.isAssignableFrom(targetType);
    }

    /**
     * 判断对象和目标对象同为Map类型
     * @param sourceType 源对象类型
     * @param targetType 目标对象类型
     */
    private static <S, T> boolean isMap(Class<S> sourceType, Class<T> targetType) {
        return Map.class.isAssignableFrom(sourceType) && Map.class.isAssignableFrom(targetType);
    }

    /**
     * 判断对象和目标对象同为Array类型
     * @param sourceType 源对象类型
     * @param targetType 目标对象类型
     */
    private static <S, T> boolean isArray(Class<S> sourceType, Class<T> targetType) {
        return sourceType.isArray() && targetType.isArray();
    }
}