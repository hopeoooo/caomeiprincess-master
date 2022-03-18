package com.caomei.controller.admin;

import com.caomei.controller.BaseController;
import com.caomei.dto.QueryPage;
import com.caomei.dto.ResponseCode;
import com.caomei.entity.Links;
import com.caomei.annotation.BodyMapping;
import com.caomei.annotation.Log;
import com.caomei.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LinksController extends BaseController {
    @RequestMapping("admin/links")
    public String pageLinks(Model model){
        init(model);
        return "admin/page/links";
    }

    @Autowired
    private LinksService linksService;
    @BodyMapping("links/findByPage")
    public ResponseCode findByPage(QueryPage queryPage, Links link){
       return ResponseCode.success(super.selectByPageNumSize(queryPage, () -> linksService.findByPage(link)));
    }

    @BodyMapping("links/save")
    @Log("新增友链")
    public ResponseCode save(@RequestBody Links links){
        linksService.save(links);
        return ResponseCode.success();
    }
    @BodyMapping("links/update")
    @Log("修改又链")
    public ResponseCode update(@RequestBody Links links){
        linksService.updateAll(links);
        return ResponseCode.success();
    }
    @BodyMapping("links/delete")
    @Log("删除友链")
    public ResponseCode delete(@RequestBody List<Long> ids){
        linksService.batchDelete(ids,"id",Links.class);
        return ResponseCode.success();
    }
    @BodyMapping("links/findById")
    public ResponseCode findById(Long id){
        return ResponseCode.success(linksService.selectByKey(id));
    }
}
