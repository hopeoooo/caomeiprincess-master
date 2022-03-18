package com.caomei.controller.admin;

import com.caomei.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController extends BaseController {
    @RequestMapping("admin/cover")
    public String showCover(Model model){
        init(model);
        return "admin/page/cover";
    }


}
