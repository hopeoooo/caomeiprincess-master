package com.caomei.service;

import com.caomei.common.service.BaseService;
import com.caomei.entity.ArticleTags;
import com.caomei.entity.Tags;

import java.util.List;

public interface ArticleTagService extends BaseService<ArticleTags> {
    void deleteByArticleId(Long id);

    List<Tags> findByArticleId(Long id);
}
