package com.caomei.music.util;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SeachUtil {
    @Value("${netease.Seach}")
    private  String neteaseSeach;
    //查询接口
    public String seachMusics(String source, String count, String pages, String name){
        try {
//            switch (site) {
//                case "netease":
                    Map<String, String> params = new HashMap<>();
                    params.put("source",source);
                    params.put("count",count);
                    params.put("pages",pages);
                    params.put("name",name);
                    params.put("types","search");
                    //发送请求
                    Connection.Response response = ConnUtil.getConnect(neteaseSeach, params);
                    String list = response.body();
                    return list;
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
