package com.global.common.utils.http;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-03-11 下午 06:01
 * @Version: v1.0
 */
@Slf4j
public class HttpClientUtil {

    private static final int RETRY_MAX_COUNT = 3;
    private static PoolingHttpClientConnectionManager poolConnectionManager;
    private static RequestConfig requestConfig;

    static {
        final int CONNECT_TIMEOUT = 200000;
        final int SOCKET_TIMEOUT = 200000;
        final int CONNECT_REQUEST_TIMEOUT = 200000;
        poolConnectionManager = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        poolConnectionManager.setMaxTotal(20);
        poolConnectionManager.setDefaultMaxPerRoute(poolConnectionManager.getMaxTotal());
        poolConnectionManager.setValidateAfterInactivity(1000);
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        configBuilder.setConnectTimeout(CONNECT_TIMEOUT);
        configBuilder.setSocketTimeout(SOCKET_TIMEOUT);
        configBuilder.setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT);
        requestConfig = configBuilder.build();
    }

    /**
     * Get方式请求连接
     * @param url 请求地址
     * @return 响应返回结果
     */
    public static String get(String url) throws Exception {
        return get(url, null, null, false);
    }

    /**
     * Get方式请求连接
     * @param url 请求地址
     * @param headers 请求头信息
     * @return 响应返回结果
     */
    public static String get(String url, Map<String, String> headers) throws Exception {
        return get(url, null, headers, false);
    }


    /**
     * Get方式请求连接
     * @param url 请求地址
     * @param params 请求参数
     * @param headers 请求头信息
     * @return 响应返回结果
     */
    public static String get(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        return get(url, params, headers, false);
    }

    /**
     * Get方式请求连接
     * @param url 请求地址
     * @param params 请求参数
     * @param headers 请求头信息
     * @param isSSL 是否启用SSL
     * @return 响应返回结果
     */
    public static String get(String url, Map<String, Object> params, Map<String, String> headers, boolean isSSL) throws Exception {
        log.debug("请求地址: {}, 请求参数: {}, 请求头信息: {}, 是否启用SSL: {}", url, JSON.toJSONString(params), JSON.toJSONString(headers), isSSL);
        CloseableHttpClient httpClient = createHttpClient(isSSL);
        if (params != null && !params.isEmpty()) {
            StringBuffer buffer = new StringBuffer(url);
            buffer.append("?");
            params.keySet().forEach(key -> buffer.append(key).append("=").append(params.get(key)).append("&"));
            buffer.deleteCharAt(buffer.lastIndexOf("&"));
            url = buffer.toString();
        }
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        setHeader(httpGet, headers);
        return getResponseMessage(httpClient, httpGet);
    }

    /**
     * POST方式调用
     * @param url 请求地址
     * @param json 请求参数
     * @return 响应返回结果
     */
    public static String postJson(String url, String json) throws Exception {
        return postJson(url, json, null, false);
    }

    /**
     * POST方式调用
     * @param url 请求地址
     * @param json 请求参数
     * @param headers 请求头信息
     * @return 响应返回结果
     */
    public static String postJson(String url, String json, Map<String, String> headers) throws Exception {
        return postJson(url, json, headers, false);
    }

    /**
     * POST方式调用
     * @param url 请求地址
     * @param json 请求参数
     * @param headers 请求头信息
     * @param isSSL 是否启用SSL
     * @return 响应返回结果
     */
    public static String postJson(String url, String json, Map<String, String> headers, boolean isSSL) throws Exception {
        log.debug("请求地址: {}, 请求参数: {}, 请求头信息: {}, 是否启用SSL: {}", url, json, JSON.toJSONString(headers), isSSL);
        CloseableHttpClient httpClient = createHttpClient(isSSL);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        setHeader(httpPost, headers);
        if (json != null)
            httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        return getResponseMessage(httpClient, httpPost);
    }


    /**
     * 判断当前请求是否可以发起重试
     */
    private static HttpRequestRetryHandler retryHandler() {
        return (exception, count, context) -> count < RETRY_MAX_COUNT;
    }

    /**
     * SSL连接工厂
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    private static SSLConnectionSocketFactory connectionSocketFactory() throws KeyManagementException, NoSuchAlgorithmException {
        return new SSLConnectionSocketFactory(new SSLContextBuilder().build());
    }

    /**
     * 创建Http客户端链接
     * @param isSSL 是否需要SSL认证
     */
    private static CloseableHttpClient createHttpClient(boolean isSSL) throws NoSuchAlgorithmException, KeyManagementException {
        return isSSL ? HttpClients.custom().setRetryHandler(retryHandler()).setSSLSocketFactory(connectionSocketFactory()).build() :
                HttpClients.custom().setRetryHandler(retryHandler()).setConnectionManager(poolConnectionManager).build();
    }

    /**
     * 设置头信息
     * @param request
     * @param headers
     */
    private static <REQUEST extends HttpRequestBase> void setHeader(REQUEST request, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            headers.keySet().forEach(key -> request.setHeader(key, headers.get(key)));
        }
    }

    /**
     * 获取HTTP响应消息
     * @param httpClient 请求链接
     * @param request 请求信息
     */
    private static String getResponseMessage(CloseableHttpClient httpClient, HttpRequestBase request) {
        String result = null;
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            String message = response.getStatusLine().getReasonPhrase();
            log.debug("HTTP响应状态码: {}, 响应描述: {}", statusCode, message);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }

}
