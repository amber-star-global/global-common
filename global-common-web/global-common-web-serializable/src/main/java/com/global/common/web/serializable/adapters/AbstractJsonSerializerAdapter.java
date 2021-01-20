package com.global.common.web.serializable.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;

/**
 * 处理Gson序列化抽象类
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-16 下午 05:29
 * @Version: v1.0
 */
public abstract class AbstractJsonSerializerAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    protected abstract T getValue(JsonElement jsonElement) throws JsonParseException;

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        String valueString = jsonElement.getAsString();
        String nullValue = "null";
        if (StringUtils.hasText(valueString) && !valueString.equals(nullValue)) {
            return getValue(jsonElement);
        }
        return null;
    }

}
