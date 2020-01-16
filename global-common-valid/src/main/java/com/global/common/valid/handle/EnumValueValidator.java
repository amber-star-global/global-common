package com.global.common.valid.handle;

import com.global.common.valid.annotations.EnumValue;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-01-16 上午 10:21
 * @Version: v1.0
 */
@Slf4j
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {


    private Class<? extends Enum<?>> enumClass;

    private String enumMethod;

    @Override
    public void initialize(EnumValue enumValue) {
        this.enumClass = enumValue.value();
        this.enumMethod = enumValue.enumMethod();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null)
            return false;
        try {
            Class<?> valueClass = value.getClass();
            Method method = enumClass.getMethod(enumMethod, valueClass);
            Object obj = method.invoke(null, value);
            if (obj != null)
                return true;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
