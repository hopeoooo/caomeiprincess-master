package com.caomei.service;

import com.caomei.common.service.BaseService;
import com.caomei.entity.Tags;

import java.util.List;

public interface TagService extends BaseService<Tags> {
    int findAllCount();

    List<Tags> findByArticleId(Long id);

    void save(Tags tags);

    Tags findByName(String name);

    List<Tags> findAll();

    void delete(List<Long> ids);

    Tags findById(Long id);

    void update(Tags tags);
}
