package com.global.common.utils.constants.tools.verify;


import com.global.common.utils.constants.tools.copy.ReflectUtil;
import com.global.common.utils.constants.tools.verify.annotation.VerifyObject;
import com.global.common.utils.constants.tools.verify.annotation.VerifyValue;
import com.google.common.base.VerifyException;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-02 上午 11:06
 * @Version: v1.0
 */
public class VerifyUtil {

    /**
     * 校验任何对象是否可以使用
     * 校验范围：
     *      集合对象：集合是否为null，size() > 0
     *      String对象：String是否为null，是否是""
     *      包装类对象：是否可以转成基本数据类型
     *      对象：是否为null
     * @param object
     * @return
     */
    private static boolean verify(Object object){
        return (object instanceof Collection) ? verifyCollection((Collection<?>) object) :
                (object instanceof Map) ? verifyMap((Map<?, ?>) object) :
                        (object instanceof Object[]) ? verifyArray((Object[])object) :
                                (object instanceof String) ? verifyString((String) object) :
                                        verifyObject(object);
    }

    /**
     * 校验任何对象是否可以使用
     * 校验范围：
     *      集合对象：集合是否为null，size() > 0
     *      String对象：String是否为null，是否是""
     *      包装类对象：是否可以转成基本数据类型
     *      对象：是否为null
     * @param object 任意对象
     */
    public static boolean verify(Object... object){
        if (!verifyArray(object)) {
            return false;
        }
        for (Object o : object) {
            if (!verify(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验集合类型
     * @param collection 集合
     */
    public static boolean verifyCollection(Collection collection){
        return collection != null && collection.size() > 0;
    }

    /**
     * 校验集合类型
     * @param collections 集合
     */
    public static boolean verifyCollection(Collection... collections){
        if (!verifyArray(collections)) {
            return false;
        }
        for (Collection<?> collection : collections) {
            if (!verifyCollection(collection)) {
                return false;
            }
        }
        return true;
    }
    /**
     * 校验Map类型
     * @param map Map集合
     */
    public static boolean verifyMap(Map<?, ?> map){
        return map != null && map.size() > 0;
    }

    /**
     * 校验字符串
     * @param string 字符串对象
     */
    public static boolean verifyString(String string){
        return string != null && !"".equals(string.trim());
    }

    /**
     * 校验数组
     * @param array 数组对象
     */
    public static boolean verifyArray(Object[] array){
        return array != null && array.length > 0;
    }

    /**
     * 校验对象
     * @param object 任意对象
     */
    public static boolean verifyObject(Object object){
        return object != null;
    }


    /**
     * 校验对象参数值
     * @param object 任意对象
     */
    public static <T> void verifyObjectValue(final T object) {
        if (!verifyObject(object)) {
            throw new VerifyException("当前对象为空!");
        }
        Field[] fields = ReflectUtil.getAllFields(object);
        for (Field field : fields) {
            if (verifySerializableField(field))
                continue;
            // VerifyObject优先级高于VerifyValue
            VerifyObject objAnn = field.getAnnotation(VerifyObject.class);
            VerifyValue valAnn = field.getAnnotation(VerifyValue.class);
            if (objAnn == null && valAnn == null) {
                continue;
            }
            if (objAnn != null) {
                verifyObjectField(object, field, objAnn);
            } else {
                verifyValueField(object, field, valAnn);
            }
        }
    }

    /**
     * 判断字段是否需要校验
     * @param field 当前字段，判断当前字段是否为序列化字段，是否存在校验注解
     * @return serialVersionUID，不存校验注解返回false
     */
    public static boolean verifySerializableField(final Field field) {
        return "serialVersionUID".equalsIgnoreCase(field.getName());
    }

    /**
     * 处理集合对象校验
     * @param collection 对象集合
     */
    private static void verifyCollectionObject(Collection collection) {
        for (Object objValue : collection) {
            verifyObjectValue(objValue);
        }
    }

    /**
     * 校验对象
     * @param field 字段是一个实体, 实体的字段需要进一步校验
     * @param objAnn  为true时,强校验 当前对象不可为空!, false时,允许当前对象为空!,为空时不校验对象离的参数
     */
    private static <T> void verifyObjectField(final T object, final Field field, final VerifyObject objAnn) {
        Object value = ReflectUtil.getFieldValue(object, field);
        boolean verifyObject = verify(value);
        if (!verifyObject && objAnn.notNull()) { // 当前字段为空, 并且该字段不允许为空!
            throw new VerifyException(String.format("[参数校验]当前参数[%s]校验异常, 异常信息: %s", objAnn.name(), objAnn.message()));
        } else if (verifyObject) { // 当前字段不为空, 目前只支持字段是集合类型
            Class genericClass = ReflectUtil.getCollectionGeneric(object, field);
            if (verifyObject(genericClass)) { // 如果是集合,则需要判断当前字段对象里的字段
                // 需要进入集合的对象进行下一步校验
                verifyCollectionObject((Collection) Objects.requireNonNull(value));
            } else {
                verifyObjectValue(value);
            }
        }
    }

    /**
     * 校验value
     * @param field 字段是一个java类型的对象, 校验非空或者数组校验元素大小
     * @param valAnn 这个注解修饰的字段值必须不可以为空, 如果是集合则判断size大小
     */
    private static <T> void verifyValueField(final T object, final Field field, final VerifyValue valAnn) {
        if (!verify(ReflectUtil.getFieldValue(object, field))) {
            throw new VerifyException(String.format("[参数校验]当前参数[%s]校验异常, 异常信息: %s", valAnn.name(), valAnn.message()));
        }
    }

}