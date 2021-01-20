package com.global.common.web.serializable;

import com.global.common.web.serializable.adapters.JsonSerializerBigDecimalAdapter;
import com.global.common.web.serializable.adapters.JsonSerializerDateAdapter;
import com.global.common.web.serializable.adapters.JsonSerializerDoubleAdapter;
import com.global.common.web.serializable.adapters.JsonSerializerIntegerAdapter;
import com.global.common.web.serializable.adapters.JsonSerializerLongAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-16 下午 05:16
 * @Version: v1.0
 */
public class GsonUtil {

    private static Gson gson;

    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        return gson().fromJson(json, typeToken.getType());
    }

    public static <T> TypeToken<T> getTypeToken(Class<T> targetClass) {
        return new TypeToken<T>() {};
    }

    public synchronized static Gson gson() {
        if (Objects.isNull(gson)) {
            synchronized (GsonUtil.class) {
                GsonBuilder builder = new GsonBuilder();
                builder
                        .registerTypeAdapter(Integer.class, new JsonSerializerIntegerAdapter())
                        .registerTypeAdapter(Long.class, new JsonSerializerLongAdapter())
                        .registerTypeAdapter(Double.class, new JsonSerializerDoubleAdapter())
                        .registerTypeAdapter(BigDecimal.class, new JsonSerializerBigDecimalAdapter())
                        .registerTypeAdapter(Date.class, new JsonSerializerDateAdapter())
                ;
                gson = builder.create();
            }
        }
        return gson;
    }
}
