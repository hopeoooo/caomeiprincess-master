package com.caomei.controller.admin;

import com.caomei.dto.QueryPage;
import com.caomei.dto.ResponseCode;
import com.caomei.entity.LoginLog;
import com.caomei.annotation.BodyMapping;
import com.caomei.annotation.Log;
import com.caomei.controller.BaseController;
import com.caomei.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginlogController extends BaseController {

    @RequestMapping("admin/loginlog")
    public String pageLoginlog(Model model){
        init(model);
        return "admin/page/loginlog";
    }

    @Autowired
    private LoginLogService loginLogService;
    @BodyMapping("loginlog/list")
    public ResponseCode list(QueryPage queryPage, LoginLog loginLog){
        return ResponseCode.success(selectByPageNumSize(queryPage,()-> loginLogService.findByPage(loginLog)));
    }

    @BodyMapping("loginlog/delete")
    @Log("删除登入日志")
    public ResponseCode delete(@RequestBody List<Long> ids){
        loginLogService.batchDelete(ids,"id",LoginLog.class);
        return ResponseCode.success();
    }
}
