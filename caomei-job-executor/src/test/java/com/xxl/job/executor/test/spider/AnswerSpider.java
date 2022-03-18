package com.xxl.job.executor.test.spider;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class AnswerSpider {
    static String SendGet(String url) {
        // 定义一个字符串用来存储网页内容
        String result = "";
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
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
    // 获取所有的编辑推荐的知乎内容
    static void GetRecommendations(String content) {
        // 预定义一个ArrayList来存储结果
        Document doc = Jsoup.parse(content);
        Elements elements = doc.getElementsByClass("RichContent-inner");
        System.out.println(doc.getElementsByClass("QuestionHeader-detail").text());
//        for (int i = 0; i < 1; ++i) {
//            Element element = elements.get(i); // 获取第i个元素
////            Element a_element = element.getElementsByClass("content").get(0); // 返回元素的文本
//            System.out.println("Answer：" + element.text());
//        }
    }

    public static void main(String[] args) {
        // 定义即将访问的链接
        String url = "https://www.zhihu.com/node/ExploreRecommendListV2";
        // 访问链接并获取页面内容
        String content = AnswerSpider.SendGet(url);
        System.out.println(content);
//        AnswerSpider.GetRecommendations(content);
    }
}
