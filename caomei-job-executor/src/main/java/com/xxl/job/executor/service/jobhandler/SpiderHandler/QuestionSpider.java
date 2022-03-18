package com.xxl.job.executor.service.jobhandler.SpiderHandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.entity.Answer;
import com.xxl.job.executor.entity.Question;
import com.xxl.job.executor.mapper.AnswerMapper;
import com.xxl.job.executor.mapper.QuestionMapper;
import com.xxl.job.executor.util.HttpCilentUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@JobHandler(value="QuestionHandler")
@Component
public class QuestionSpider extends IJobHandler {
    @Value("${zhihuUrl}")
    private String url;

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        Document doc = Jsoup.parse(HttpCilentUtil.SendGet(url));
        Elements elements = doc.getElementsByAttributeValue("data-type","Answer");
        saveQuestionAndAnswer(elements,0);
        return ReturnT.SUCCESS;
    }

    private void saveQuestionAndAnswer(Elements elements,int i){
        if(i<elements.size()){
            try {
                Question question = new Question();
                Answer answer = new Answer();

                Element element = elements.get(i); // 获取第i个元素
                Element q_element = element.getElementsByAttributeValue("data-za-element-name","Title").get(0); // 返回元素的文本
                Element a_element = element.getElementsByClass("content").get(0); // 返回元素的文本

                question.setTitle(q_element.text());
                // /question/319356460/answer/741273564
                question.setQid(Integer.valueOf(q_element.attr("href").split("/")[2]));
                question.setUpdateTime(new Date());
                //question.setText(q_element.attr("href"));

                answer.setText(a_element.text());
                answer.setQid(Integer.valueOf(q_element.attr("href").split("/")[2]));
                answer.setAid(Integer.valueOf(q_element.attr("href").split("/")[4]));
                answer.setCreateTime(new Date());
                questionMapper.insert(question);
                answerMapper.insert(answer);
            }catch (Exception e){
                XxlJobLogger.log(e);
            }finally {
                i++;
                saveQuestionAndAnswer(elements,i);
            }
        }
    }
}
