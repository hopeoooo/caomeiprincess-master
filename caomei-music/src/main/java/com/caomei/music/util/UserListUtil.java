package com.caomei.music.util;

import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserListUtil {
    @Value("${netease.UserList}")
    private String neteaseUserList;

    public String getUserList(String uid) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("uid",uid);
            params.put("types","userlist");
            //发送请求
            Connection.Response response = ConnUtil.getConnect(neteaseUserList, params);
            String list = response.body();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
