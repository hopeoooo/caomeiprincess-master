package com.caomei.music.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

public class ConnUtil {
    //执行 请求
    public static Connection.Response getConnect(String url, Map params) throws IOException {
        Connection.Response response = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
                .header("Accept", "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("Content-Length", "42")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("Connection", "keep-alive")
                .header("Cookie","UM_distinctid=16b46b7d56c11e-0128d0057c4b01-37647e03-1aeaa0-16b46b7d56e512; CNZZDATA1275011118=445982280-1560254478-%7C1560254478; Hm_lvt_e941dfc465779f2553a65876b7d920fe=1560259582; Hm_lpvt_e941dfc465779f2553a65876b7d920fe=1560259582")
                .data(params)
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .timeout(10000*50)
                .execute();
        return response;
    }
}
