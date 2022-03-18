package com.xxl.job.executor.service.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;

@JobHandler(value = "MusicJobHandler")
@Component
public class MusicJobHandler extends IJobHandler {
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        return null;
    }
}
