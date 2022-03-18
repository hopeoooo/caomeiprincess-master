package com.caomei.music.util;

import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class UrlUtil {

    @Value("${netease.Url}")
    private String neteaseUrl;

    public String getMusic(String source,String id){
        try {
//            switch (source) {
//                case "netease":
                    Map<String, String> params = new HashMap<>();
                    params.put("id",id);
                    params.put("source",source);
                    params.put("types","url");
                    //发送请求
                    Connection.Response response = ConnUtil.getConnect(neteaseUrl, params);
                    String list = response.body();
                    return list;
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
