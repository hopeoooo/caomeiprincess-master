package com.caomei.service.impl;

import com.caomei.common.service.impl.BaseServiceImpl;
import com.caomei.entity.ArticleTags;
import com.caomei.entity.Tags;
import com.caomei.mapper.ArticleTagMapper;
import com.caomei.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTagServiceImpl extends BaseServiceImpl<ArticleTags> implements ArticleTagService{

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public void deleteByArticleId(Long id) {
        articleTagMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Tags> findByArticleId(Long id) {
        return articleTagMapper.findByArticleId(id);
    }
}
