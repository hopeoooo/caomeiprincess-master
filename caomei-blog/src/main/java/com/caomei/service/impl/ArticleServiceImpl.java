package com.caomei.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.caomei.common.User;
import com.caomei.common.service.impl.BaseServiceImpl;
import com.caomei.dto.ArticleArchives;
import com.caomei.entity.*;
import com.caomei.service.*;
import com.caomei.exception.GlobalException;
import com.caomei.mapper.ArticleMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public List<Article> findAll() {
        Example example = new Example(Article.class);
        example.setOrderByClause("`id` desc");
        return articleMapper.selectByExampleAndRowBounds(example, new RowBounds(0, 8));
    }

    @Override
    public Map<String, Object> findByPageForSite(int pageCode, int pageSize) {
        PageHelper.startPage(pageCode, pageSize);
        Page<Article> page = articleMapper.findByPageForSite();
        List<Article> articleList = page.getResult();
        findInit(articleList);
        Map<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("data", articleList);
        return map;
    }

    @Override
    public int findAllCount() {
        return articleMapper.selectCount(new Article());
    }

    @Override
    @Transactional
    public void save(Article article) {
        try {
            if (article.getState() == "1") {
                article.setPublishTime(new Date());
            }
            article.setAuthor(((User) SecurityUtils.getSubject().getPrincipal()).getUsername());
            article.setEditTime(new Date());
            article.setCreateTime(new Date());
            articleMapper.insert(article);
            article.setId(articleMapper.getLastId());
            updateArticleCategoryTags(article);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public List<Article> findByPage(Article article) {
        Example example = new Example(Article.class);
        if (!StringUtils.isEmpty(article.getTitle())) {
            example.createCriteria().andLike("title", "%" + article.getTitle() + "%");
        }
        List<Article> list = articleMapper.selectByExample(example);
        findInit(list);
        return list;
    }

    @Autowired
    private ArticleCategoryService articleCategoryService;
    @Autowired
    private ArticleTagService articleTagService;
    @Override
    public void delete(List<Long> ids) {
        if(!ids.isEmpty()){
            ids.forEach(id->{
                articleMapper.deleteByPrimaryKey(id);
                //????????????-??????????????????
                articleCategoryService.deleteByArticleId(id);
                //????????????-??????????????????
                articleTagService.deleteByArticleId(id);
            });
        }
    }

    @Override
    public Article findById(Long id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Article article) {
        try {
            this.updateNotNull(article);
            updateArticleCategoryTags(article);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void addViews(Long id) {
        if (!id.equals(null) && id != 0) {
            try {
                articleMapper.addViews(id);
            } catch (Exception e) {
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }
    }

    @Override
    public List<ArticleArchives> findArchives() {
        List<ArticleArchives> articleArchivesList = new ArrayList<ArticleArchives>();
        try {
            List<String> dates = articleMapper.findArchivesDates();
            dates.forEach(date -> {
                List<Article> articleList = articleMapper.findArchivesByDate(date);
                ArticleArchives articleArchives = new ArticleArchives(date, articleList);
                articleArchivesList.add(articleArchives);
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return articleArchivesList;
    }

    /**
     * ????????????-??????-???????????????????????????
     *
     * @param article
     */
    @Transactional
    private void updateArticleCategoryTags(Article article) {
        if (article.getId() != 0) {
            if (article.getCategory() != null) {
                //????????????????????????????????????????????????????????????????????????????????????
                categoryService.save(new Category(article.getCategory()));

                //????????????????????????????????????-?????????????????????
                Category category = categoryService.findByName(article.getCategory());
                articleCategoryService.save(new ArticleCategory(article.getId(), category.getId()));
            }
            if (article.getTags() != null) {
                //??????????????????????????????????????????????????????????????????????????????
                List<String> list = (List) JSONArray.parse(article.getTags()); //????????????????????????JSON??????????????????????????????
                if (list.size() > 0) {
                    list.forEach(name -> {
                        tagService.save(new Tags(name));
                        Tags tag = tagService.findByName(name); //???????????????????????????????????????????????????????????????????????????

                        if (tag != null) {
                            //??????????????????????????????????????????????????????-??????????????????
                            articleTagService.save(new ArticleTags(article.getId(), tag.getId()));
                        }
                    });
                }
            }
        }
    }


    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    private void findInit(List<Article> list) {
        if (!list.isEmpty()) {
            list.forEach(article -> {
                List<Category> categoryList = categoryService.findByArticleId(article.getId());
                if (categoryList.size() > 0) {
                    article.setCategory(categoryList.get(0).getName());
                }
                List<Tags> tagList = tagService.findByArticleId(article.getId());
//                List<String> stringList = new ArrayList<>();
//                tagList.forEach(tags -> {
//                    stringList.add(tags.getName());
//                });
                article.setTags(JSON.toJSONString(tagList));
            });
        }
    }
}
