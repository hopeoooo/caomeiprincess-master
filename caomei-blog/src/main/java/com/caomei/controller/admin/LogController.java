package com.caomei.controller.admin;

import com.caomei.controller.BaseController;
import com.caomei.dto.QueryPage;
import com.caomei.dto.ResponseCode;
import com.caomei.entity.SysLog;
import com.caomei.annotation.BodyMapping;
import com.caomei.annotation.Log;
import com.caomei.exception.GlobalException;
import com.caomei.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LogController extends BaseController {
    @RequestMapping("admin/log")
    public String pageLog(Model model){
        init(model);
        return "admin/page/log";
    }

    @Autowired
    private LogService logService;
    @BodyMapping("log/list")
    public ResponseCode list(QueryPage queryPage, SysLog log){

        return ResponseCode.success(selectByPageNumSize(queryPage,()->logService.findByPage(log)));
    }

    @Log("删除系统日志")
    @BodyMapping("log/delete")
    //@RequiresPermissions("log:delete")
    public ResponseCode delete(@RequestBody List<Long> ids) {
        try {
            logService.batchDelete(ids,"id",SysLog.class);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
