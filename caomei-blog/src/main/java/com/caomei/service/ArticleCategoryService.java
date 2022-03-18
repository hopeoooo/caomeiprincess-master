package com.caomei.service;

import com.caomei.common.service.BaseService;
import com.caomei.entity.ArticleCategory;

public interface ArticleCategoryService extends BaseService<ArticleCategory> {
    void deleteByArticleId(Long id);
}
