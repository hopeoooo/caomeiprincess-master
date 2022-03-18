package com.caomei.service;

import com.caomei.common.service.BaseService;
import com.caomei.entity.Links;

import java.util.List;

public interface LinksService extends BaseService<Links> {
    int findAllCount();

    List<Links> findByPage(Links link);
}
