package com.caomei.service.impl;

import com.caomei.common.service.impl.BaseServiceImpl;
import com.caomei.entity.ArticleCategory;
import com.caomei.exception.GlobalException;
import com.caomei.mapper.ArticleCategoryMapper;
import com.caomei.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategory> implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    public void deleteByArticleId(Long id) {
        if(id!=null && id!=0){
            articleCategoryMapper.deleteByPrimaryKey(id);
        }else{
            throw new GlobalException("参数错误！");
        }
    }
}
