package com.caomei.music.service;


import com.caomei.common.User;

public interface UserService{
    User findByName(String username);
}
