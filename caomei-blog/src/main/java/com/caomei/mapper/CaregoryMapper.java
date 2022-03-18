package com.caomei.mapper;

import com.caomei.common.config.MyMapper;
import com.caomei.entity.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CaregoryMapper extends MyMapper<Category> {

    @Select("SELECT c.name FROM tb_article a, tb_category c, tb_article_category ac" +
            " WHERE a.id = ac.article_id AND c.id = ac.category_id AND a.id = #{id}")
    List<Category> findCategoryByArticleId(Long id);
}
