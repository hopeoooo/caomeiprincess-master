package com.caomei.controller.admin;

import com.caomei.controller.BaseController;
import com.caomei.dto.QueryPage;
import com.caomei.dto.ResponseCode;
import com.caomei.entity.Tags;
import com.caomei.annotation.BodyMapping;
import com.caomei.annotation.Log;
import com.caomei.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagsController extends BaseController {
    @Autowired
    private TagService tagService;
    @BodyMapping("tags/findByPage")
    public ResponseCode findTagsByPage(QueryPage queryPage){
        return ResponseCode.success(selectByPageNumSize(queryPage,()->tagService.findAll()));
    }

    @BodyMapping("tags/delete")
    @Log("删除标签")
    public ResponseCode delete(@RequestBody List<Long> ids){
        tagService.delete(ids);
        return ResponseCode.success();
    }

    @BodyMapping("tags/save")
    @Log("新增标签")
    public ResponseCode delete(@RequestBody Tags tags){
        tagService.save(tags);
        return ResponseCode.success();
    }

    @BodyMapping("tags/update")
    @Log("修改标签")
    public ResponseCode update(@RequestBody Tags tags){
        tagService.update(tags);
        return ResponseCode.success();
    }

    @BodyMapping("tags/findById")
    public ResponseCode findById(@RequestParam("id") Long id){
        return ResponseCode.success(tagService.findById(id));
    }
}
