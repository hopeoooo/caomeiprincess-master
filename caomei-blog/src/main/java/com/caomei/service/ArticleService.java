package com.caomei.service;

import com.caomei.common.service.BaseService;
import com.caomei.dto.ArticleArchives;
import com.caomei.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService extends BaseService<Article> {

    List<Article> findAll();

    Map<String, Object> findByPageForSite(int pageCode, int pageSize);

    int findAllCount();

    void save(Article article);

    List<Article> findByPage(Article article);

    void delete(List<Long> ids);

    Article findById(Long id);

    void update(Article article);

    void addViews(Long id);

    List<ArticleArchives> findArchives();
}
