package com.global.common.web.serializable.adapters;

import com.global.common.utils.constants.utils.date.DatePattern;
import com.global.common.utils.constants.utils.date.DateUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-16 下午 05:28
 * @Version: v1.0
 */
public class JsonSerializerDateAdapter extends AbstractJsonSerializerAdapter<Date> {

    /**
     * 转换date类型
     *
     * @see DatePattern
     */
    @Override
    protected Date getValue(JsonElement jsonElement) throws JsonParseException {
        if (jsonElement.isJsonPrimitive()) {
            return DateUtil.getDate(jsonElement.getAsJsonPrimitive().getAsLong());
        } else {
            return Arrays.stream(DatePattern.values()).map(pattern -> DateUtil.formatting(jsonElement.getAsString(), pattern))
                    .filter(Objects::nonNull).findFirst().orElse(null);
        }
    }

    @Override
    public JsonElement serialize(Date source, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(source.getTime());
    }
}
