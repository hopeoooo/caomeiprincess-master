package com.caomei.knowledge.service.Impl;

import com.caomei.knowledge.entity.Question;
import com.caomei.knowledge.mapper.QuestionMapper;
import com.caomei.knowledge.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Override
    public List<Question> findQuestion() {
        List<Question> list = questionMapper.selectQuestion();
        return list;
    }
}
