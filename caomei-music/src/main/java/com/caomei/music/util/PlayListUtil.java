package com.caomei.music.util;

import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class PlayListUtil {

    @Value("${netease.PlayList}")
    private String neteasePlayList;

    public String getPlayList(String id) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id",id);
            params.put("types","playlist");
            //发送请求
            Connection.Response response = ConnUtil.getConnect(neteasePlayList, params);
            String list = response.body();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
