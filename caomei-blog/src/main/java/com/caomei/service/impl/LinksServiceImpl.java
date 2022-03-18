package com.caomei.service.impl;

import com.caomei.common.service.impl.BaseServiceImpl;
import com.caomei.entity.Links;
import com.caomei.mapper.LinksMapper;
import com.caomei.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinksServiceImpl extends BaseServiceImpl<Links> implements LinksService{

    @Autowired
    private LinksMapper linksMapper;
    @Override
    public int findAllCount() {
        return linksMapper.selectCount(new Links());
    }

    @Override
    public List<Links> findByPage(Links link) {
        return linksMapper.select(link);
    }
}
