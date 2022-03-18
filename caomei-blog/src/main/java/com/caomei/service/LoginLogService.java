package com.caomei.service;

import com.caomei.common.service.BaseService;
import com.caomei.entity.LoginLog;

import java.util.List;

public interface LoginLogService extends BaseService<LoginLog> {
   void saveLog(LoginLog log);

    List<LoginLog> findByPage(LoginLog loginLog);
}
