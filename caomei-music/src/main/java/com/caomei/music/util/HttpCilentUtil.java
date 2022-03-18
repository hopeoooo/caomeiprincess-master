package com.caomei.music.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpCilentUtil {
    public static String SendGet(String url) {
        // 定义一个字符串用来存储网页内容
        String result = "";
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "PostmanRuntime/7.29.0");
        httpGet.setHeader("Accept", "*/*");
        httpGet.setHeader("Postman-Token", "397264e8-44da-4af9-a6f4-69733b4a9216");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Referer", "https://mmzztt.com/photo/page/1/");
        httpGet.setHeader("Host", "mmzztt.com");
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                result= EntityUtils.toString(responseEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
