package com.global.common.web;

import com.alibaba.fastjson.JSON;
import com.global.common.web.model.JsonHttpEntity;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-30 下午 02:42
 * @Version: v1.0
 */
@Component
public class JsonRestTemplate extends RestTemplate {

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
     * post请求
     * @param url 访问地址
     * @param req 请求对象
     * @param resClass 返回对象
     */
    public <REQ, RES> RES postForObject(String url, Map<String, ?> params, REQ req, Class<RES> resClass) {
        String resJson = super.postForObject(setUrlParams(url, params), JsonHttpEntity.toJson(req), String.class);
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

    /**
     * GET请求
     * @param url 访问地址
     * @param resClass 返回对象
     * @param params 请求参数
     */
    public <RES> RES getForObject(String url, Map<String, ?> params, Class<RES> resClass) {
        return getForObject(setUrlParams(url, params), resClass);
    }

    /**
     * 设置url后的请求参数
     * @param url 访问地址
     * @param params 请求参数
     */
    private String setUrlParams(final String url, final Map<String, ?> params) {
        if (MapUtils.isNotEmpty(params)) {
            StringBuffer sb = new StringBuffer(url);
            sb.append("?");
            Set<String> keySet = params.keySet();
            keySet.forEach(key -> {
                Object value = params.get(key);
                if (value instanceof Collection) {
                    sb.append(setCollectionParam(key, (Collection<?>) value));
                } else {
                    setKeyValue(sb, key, value);
                }
            });
            return sb.deleteCharAt(sb.lastIndexOf("&")).toString();
        }
        return url;
    }

    /**
     * 设置拼接参数格式
     * @param sb 拼接字符对象
     * @param key 参数名
     * @param value 参数值
     */
    private void setKeyValue(StringBuffer sb, final String key, final Object value) {
        sb.append(key).append("=").append(value).append("&");
    }

    /**
     * 设置value值为集合时的处理
     * @param key 参数名
     * @param values 参数值
     */
    private String setCollectionParam(final String key, final Collection<?> values) {
        StringBuffer sb = new StringBuffer();
        values.forEach(value-> setKeyValue(sb, key, value));
        return sb.toString();
    }

}
