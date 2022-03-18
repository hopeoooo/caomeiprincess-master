package com.caomei.knowledge.mapper;

import com.caomei.knowledge.entity.Question;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface QuestionMapper  extends Mapper<Question>, MySqlMapper<Question> {
    @Select("select " +
            "q.qid qId" +
            ",q.text qText" +
            ",q.title title" +
            ",q.updatetime qUpdateDate" +
            ",a.aid aId" +
            ",a.text aText" +
            ",a.createtime aCreateDate "+
            "from KW_QUESTION q " +
            "left join (select a.qid,max(a.aid) aid from KW_ANSWER a GROUP BY a.qid) t " +
            "on t.qid=q.qid " +
            "left join KW_ANSWER a " +
            "on a.aid = t.aid " +
            "where a.text is not null " +
            "order by qUpdateDate desc")
    List<Question> selectQuestion();
}
