package com.xxl.job.executor.mapper;

import com.xxl.job.executor.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}
