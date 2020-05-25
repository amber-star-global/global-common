package com.global.common.web;

import com.alibaba.fastjson.JSON;
import com.global.common.web.model.JsonHttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-30 下午 02:42
 * @Version: v1.0
 */
@Component
public class JsonRestTemplate extends RestTemplate{


    /**
     * post请求
     * @param url 访问地址
     * @param req 请求对象
     * @param resClass 返回对象
     */
    public <REQ, RES> RES postForObject(String url, REQ req, Class<RES> resClass) {
        String resJson = super.postForObject(url, JsonHttpEntity.toJson(req), String.class);
        return JSON.parseObject(resJson, resClass);
    }

    /**
     * GET请求
     * @param url 访问地址
     * @param resClass 返回对象
     * @param params 请求参数
     */
    public <RES> RES getForObject(String url, Map<String, ?> params, Class<RES> resClass) {
        String resJson = super.getForObject(url, String.class, params);
        return JSON.parseObject(resJson, resClass);
    }

    /**
     * GET请求
     * @param url 访问地址
     * @param resClass 返回对象
     */
    public <RES> RES getForObject(String url, Class<RES> resClass) {
        String resJson = super.getForObject(url, String.class);
        return JSON.parseObject(resJson, resClass);
    }

}
