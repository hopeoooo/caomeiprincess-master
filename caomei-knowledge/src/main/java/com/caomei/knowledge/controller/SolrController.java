package com.caomei.knowledge.controller;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

//@Controller
public class SolrController {
    @Autowired
    private SolrClient client;

    /**
     * 根据id删除索引
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public String delete(String id)  {
        try {
            client.deleteById(id);
            client.commit();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
    /**
     * 删除所有的索引
     * @return
     */
    @RequestMapping("deleteAll")
    @ResponseBody
    public String deleteAll(){
        try {
            client.deleteByQuery("*:*");
            client.commit();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * 根据id查询索引
     * @return
     * @throws Exception
     */
    @RequestMapping("getById")
    @ResponseBody
    public String getById(String id) throws Exception {
        SolrDocument document = client.getById(id);
        System.out.println(document);
        return document.toString();
    }

    /**
     * 综合查询: 在综合查询中, 有按条件查询, 条件过滤, 排序, 分页, 高亮显示, 获取部分域信息
     * @return
     */
    @RequestMapping("search")
    @ResponseBody
    public Map<String, Map<String, List<String>>> search(String param){
        try {
            SolrQuery params = new SolrQuery();
            //查询条件, 这里的 q 对应 下面图片标红的地方
            params.setQuery("title:"+ param);
            params.setQuery("qText:"+ param);
            //排序
            params.addSort("qId", SolrQuery.ORDER.desc);
            //分页
            params.setStart(0);
            params.setRows(5);
            params.setHighlight(true);
            params.addHighlightField("title");// 高亮字段
            params.setHighlightSimplePre("<font color='red'>");//标记，高亮关键字前缀
            params.setHighlightSimplePost("</font>");//后缀

            QueryResponse queryResponse = client.query(params);

            SolrDocumentList results = queryResponse.getResults();
            long numFound = results.getNumFound();

            System.out.println(numFound);

            //获取高亮显示的结果, 高亮显示的结果和查询结果是分开放的
            Map<String, Map<String, List<String>>> highlight = queryResponse.getHighlighting();

            for (SolrDocument result : results) {
                System.out.print(result.get("qId")+"      ");
                System.out.print(result.get("title")+"      ");
                System.out.println(result.get("qUpdateDate"));
            }
            return highlight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
