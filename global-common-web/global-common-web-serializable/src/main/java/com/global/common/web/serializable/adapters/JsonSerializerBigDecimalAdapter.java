package com.global.common.web.serializable.adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-16 下午 05:43
 * @Version: v1.0
 */
public class JsonSerializerBigDecimalAdapter extends AbstractJsonSerializerAdapter<BigDecimal> {


    @Override
    protected BigDecimal getValue(JsonElement jsonElement) throws JsonParseException {
        return jsonElement.getAsBigDecimal();
    }

    @Override
    public JsonElement serialize(BigDecimal source, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(source);
    }
}
