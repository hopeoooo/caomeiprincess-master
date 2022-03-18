package com.caomei.service;

import com.caomei.common.service.BaseService;
import com.caomei.entity.Setting;
import com.caomei.common.User;

public interface UserService extends BaseService<User> {

    Setting findSetting();

    User findByName(String username);

    void updateSetting(Setting setting);
}
