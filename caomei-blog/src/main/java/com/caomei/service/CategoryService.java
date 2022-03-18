package com.caomei.service;

import com.caomei.common.service.BaseService;
import com.caomei.entity.Category;

import java.util.List;

public interface CategoryService extends BaseService<Category> {
    List<Category> findByArticleId(Long id);

    List<Category> findAll();

    void save(Category category);

    Category findByName(String category);

    void delete(List<Long> ids);

    Category findById(Long id);

    void update(Category category);
}
