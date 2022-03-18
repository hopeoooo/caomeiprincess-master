package com.caomei.music.controller;

import com.caomei.music.common.BaseController;
import com.caomei.music.common.CommonHolder;
import com.caomei.music.common.ResponseCode;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController extends BaseController {

    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("logout")
    public String loginOut(){
        getSubject().logout();
        return "login";
    }

    @RequestMapping("login")
    @ResponseBody
    public ResponseCode login(@Param("username") String username,
                              @Param("password") String password,
                              @Param("code") String code,
                              @Param("remember") String remember){
        Assert.isTrue(code.matches("[0-9]{6}"),"安全码格式错误(6位数字)");
        try {
            CommonHolder.secCode.set(Integer.parseInt(code));
            Subject subject = getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            if (remember != null) {
                if (remember.equals("true")) {
                    //说明选择了记住我
                    token.setRememberMe(true);
                } else {
                    token.setRememberMe(false);
                }
            } else {
                token.setRememberMe(false);
            }
            subject.login(token);
        }catch (Exception e){
            return ResponseCode.error("登入失败！");
        }

        return ResponseCode.success();
    }

    @RequestMapping(value = {"", "/", "/index"})
    public String index(){
        return "index";
    }
}
