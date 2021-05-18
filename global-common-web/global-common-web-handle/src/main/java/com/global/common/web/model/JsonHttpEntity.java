package com.global.common.web.model;

import com.alibaba.fastjson.JSON;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.MapUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-30 下午 02:28
 * @Version: v1.0
 */
@NoArgsConstructor
public class JsonHttpEntity extends HttpEntity<String> {

    public static <T> HttpEntity toJson(T entity) {
        return toJson(entity, null);
    }

    public static <T> HttpEntity toJson(T entity, Map<String, String> headerMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        if (MapUtils.isNotEmpty(headerMap))
            // 设置自定义Header信息
            headerMap.forEach(headers::add);
        String json = entity instanceof String ? (String) entity : JSON.toJSONString(entity);
        return new HttpEntity<>(json, headers);
    }

}
