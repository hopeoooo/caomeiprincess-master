package com.caomei.service.impl;

import com.caomei.common.service.impl.BaseServiceImpl;
import com.caomei.entity.Setting;
import com.caomei.common.User;
import com.caomei.mapper.SettingMapper;
import com.caomei.mapper.UserMapper;
import com.caomei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private SettingMapper settingMapper;
    @Override
    public Setting findSetting() {
        return settingMapper.selectAll().get(0);
    }

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

    @Override
    public void updateSetting(Setting setting) {
        settingMapper.updateByPrimaryKey(setting);
    }

}
