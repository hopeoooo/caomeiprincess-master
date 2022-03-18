package com.caomei.knowledge.controller;

import com.caomei.knowledge.entity.QueryPage;
import com.caomei.knowledge.entity.Question;
import com.caomei.knowledge.entity.ResponseCode;
import com.caomei.knowledge.service.QuestionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;

@Controller
public class IndexController{

    @Autowired
    private QuestionService questionService;
    @Autowired
    private SolrClient client;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(Model model,QueryPage queryPage,String param){
        model.addAttribute("questionList", ResponseCode.success(getQuestion(queryPage,param)));
        model.addAttribute("param", param);
        return "index";
    }

    @RequestMapping("queryQuestion")
    @ResponseBody
    public ResponseCode queryQuestion(QueryPage queryPage,String param){
        return ResponseCode.success(getQuestion(queryPage,param));
    }

    private Map<String, Object> getQuestion(QueryPage queryPage,String param){
        Map<String, Object> map = new HashMap<>();

        try {
            SolrQuery params = new SolrQuery();
            //查询条件
            if(StringUtils.isBlank(param)){
                params.setQuery("*:*");
            }else{
                params.setQuery("title:"+ param);
            }
            //排序
            params.addSort("qUpdateDate", SolrQuery.ORDER.desc);
            //分页
            params.setStart(queryPage.getPageSize()*queryPage.getPageCode());
            params.setRows(queryPage.getPageSize());
            QueryResponse queryResponse = client.query(params);

            SolrDocumentList results = queryResponse.getResults();
            Page page = new Page();
            for (SolrDocument result : results) {
                page.add(new Question((int)result.get("qId"),
                                            (int)result.get("aId"),
                                            (String) result.get("title"),
                                            (String)result.get("qText"),
                                            (String)result.get("aText"),
                                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String)result.get("qUpdateDate")),
                                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String)result.get("aCreateDate"))));
            }
            page.setTotal(results.getNumFound());
            page.setPageNum(queryPage.getPageCode());
            page.setPageSize(queryPage.getPageSize());
            map.put("rows",page);
        }catch (Exception e){
            e.printStackTrace();
            map = selectByPageNumSize(queryPage,()->questionService.findQuestion());
        }
        return map;
    }
    /**
     * Supplier是JDK8新特性
     * 特点：只有返回值，没有输入参数
     * 如：Supplier<User> user = User::new;
     * 此时并没有构造User对象，当调用`user.get()`方法才获取一个新的User构造对象
     *
     * QueryPage 是封装分页条件的entity，如果没有指定默认查询所有
     */
    private Map<String, Object> selectByPageNumSize(QueryPage page, Supplier<?> s) {
        PageHelper.startPage(page.getPageCode(), page.getPageSize());
        PageInfo<?> pageInfo = new PageInfo<>((List<?>) s.get());
        PageHelper.clearPage();
        return getData(pageInfo);
    }

    private Map<String, Object> getData(PageInfo<?> pageInfo) {
        Map<String, Object> data = new HashMap<>();
        data.put("rows", pageInfo.getList());
        return data;
    }
}
