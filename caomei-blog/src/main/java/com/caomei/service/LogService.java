package com.caomei.service;

import com.caomei.common.service.BaseService;
import com.caomei.entity.SysLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface LogService extends BaseService<SysLog> {
    @Async
    void saveLog(ProceedingJoinPoint proceedingJoinPoint, SysLog log) throws JsonProcessingException;

    List<SysLog> findByPage(SysLog log);
}
