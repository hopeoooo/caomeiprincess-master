package com.caomei.music.util;

import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class PicUtil {
    @Value("${netease.Pic}")
    private String neteasePic;

    public String getPic(String source, String id) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id",id);
            params.put("source",source);
            params.put("types","pic");
            //发送请求
            Connection.Response response = ConnUtil.getConnect(neteasePic, params);
            String list = response.body();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
