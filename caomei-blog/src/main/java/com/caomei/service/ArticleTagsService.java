package com.caomei.service;

import com.caomei.common.service.BaseService;
import com.caomei.entity.Article;
import com.caomei.entity.ArticleTags;
import com.caomei.entity.Tags;

import java.util.List;

public interface ArticleTagsService extends BaseService<ArticleTags> {

    /**
     * 根据文章ID查询文章+标签数据
     *
     * @param articleId
     * @return
     */
    List<Tags> findByArticleId(Long articleId);

    /**
     * 根据标签ID查询文章+标签数据
     *
     * @param tagId
     * @return
     */
    List<ArticleTags> findByTagId(Long tagId);

    /**
     * 根据文章ID删除
     *
     * @param id
     */
    void deleteByArticleId(Long id);

    /**
     * 根据标签ID删除
     *
     * @param id
     */
    void deleteByTagsId(Long id);

    /**
     * 根据标签名称查询关联的文章
     *
     * @param tag
     * @return
     */
    List<Article> findByTagName(String tag);
}
