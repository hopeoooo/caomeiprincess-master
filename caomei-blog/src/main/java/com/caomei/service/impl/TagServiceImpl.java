package com.caomei.service.impl;

import com.caomei.common.service.impl.BaseServiceImpl;
import com.caomei.entity.Tags;
import com.caomei.exception.GlobalException;
import com.caomei.mapper.TagMapper;
import com.caomei.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl extends BaseServiceImpl<Tags> implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public int findAllCount() {
        return tagMapper.selectCount(new Tags());
    }

    @Override
    public List<Tags> findByArticleId(Long id) {
        if (!id.equals(null) && id != 0) {
            return tagMapper.findTagByArticleId(id);
        } else {
            throw new GlobalException("参数错误");
        }
    }

    @Transactional
    @Override
    public void save(Tags tags) {
        tagMapper.insert(tags);
    }

    @Override
    public Tags findByName(String name) {
        Tags tags = new Tags();
        tags.setName(name);
        return tagMapper.select(tags).get(0);
    }

    @Override
    public List<Tags> findAll() {
        return tagMapper.selectAll();
    }

    @Override
    public void delete(List<Long> ids) {
        batchDelete(ids,"id",Tags.class);
    }

    @Override
    public Tags findById(Long id) {
        return tagMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void update(Tags tags) {
        tagMapper.updateByPrimaryKey(tags);
    }
}
