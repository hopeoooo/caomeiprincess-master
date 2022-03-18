package com.caomei.music.service.Impl;

import com.caomei.common.User;
import com.caomei.music.mapper.UserMapper;
import com.caomei.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByName(String username) {
        if (!username.isEmpty()) {
            User user = new User();
            user.setUsername(username);
            List userlist = userMapper.select(user);
            return userlist.size()>0?userMapper.select(user).get(0):new User();
        } else {
            return new User();
        }
    }
}
