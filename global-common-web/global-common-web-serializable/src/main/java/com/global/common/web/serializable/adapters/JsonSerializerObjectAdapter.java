package com.global.common.web.serializable.adapters;

import com.global.common.web.serializable.GsonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-16 下午 07:18
 * @Version: v1.0
 */
public class JsonSerializerObjectAdapter extends AbstractJsonSerializerAdapter<Object> {


    @Override
    protected Object getValue(JsonElement jsonElement) throws JsonParseException {
        return jsonElement.getAsString();
    }

    @Override
    public JsonElement serialize(Object object, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(GsonUtil.gson().toJson(object));
    }
}
