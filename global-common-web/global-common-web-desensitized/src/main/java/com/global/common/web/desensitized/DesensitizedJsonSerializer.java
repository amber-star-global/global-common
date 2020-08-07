package com.global.common.web.desensitized;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.global.common.web.desensitized.annotations.Desensitized;
import com.global.common.web.desensitized.enums.DesensitizedType;
import com.global.common.web.desensitized.handle.DesensitizedHandleFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * 脱敏json序列化处理类
 * 拿到要转换的字段, 获取字段的脱敏注解
 * 根据定义的类型, 进行不同的脱敏处理
 * 如果当前字段没有脱敏的标签, 则原值返回
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-05 下午 09:18
 * @Version: v1.0
 */
public final class DesensitizedJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private final DesensitizedHandleFactory desensitizedHandleFactory = new DesensitizedHandleFactory();

    private DesensitizedType type;

    public DesensitizedJsonSerializer() {
        this.type = null;
    }

    public DesensitizedJsonSerializer(DesensitizedType type) {
        this.type = type;
    }

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(desensitizedHandleFactory.getFormatter(type).process(value));
    }


    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (Objects.nonNull(beanProperty)) {
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                Desensitized desensitized = beanProperty.getAnnotation(Desensitized.class);
                if (Objects.isNull(desensitized)) {
                    desensitized = beanProperty.getContextAnnotation(Desensitized.class);
                }
                if (Objects.nonNull(desensitized)) {
                    return new DesensitizedJsonSerializer(desensitized.type());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }

}
