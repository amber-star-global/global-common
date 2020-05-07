package com.global.common.web.model;

import com.alibaba.fastjson.JSON;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-30 下午 02:28
 * @Version: v1.0
 */
@NoArgsConstructor
public class JsonHttpEntity extends HttpEntity<String> {

    public static <T> HttpEntity toJson(T entity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String json = JSON.toJSONString(entity);
        return new HttpEntity<>(json, headers);
    }

}
