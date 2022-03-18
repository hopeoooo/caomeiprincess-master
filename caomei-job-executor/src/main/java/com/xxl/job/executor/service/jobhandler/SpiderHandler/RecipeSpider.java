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
import java.util.concurrent.atomic.AtomicInteger;

@JobHandler(value="RecipeSpider")
@Component
public class RecipeSpider extends IJobHandler {

    @Value("${RecipeUrl}")
    private String url;

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerMapper answerMapper;

    private AtomicInteger page = new AtomicInteger(1);

    private String defultParam = "5322";
    public ReturnT<String> execute(String param) throws Exception {
        if(param!=null && !param.equals(defultParam)){
            page = new AtomicInteger(1);
        }else{
            param = defultParam;
        }
        boolean isRun = true;
        while (isRun){
            System.out.println("page:"+page);
            Document doc = Jsoup.parse(HttpCilentUtil.SendGet(url+param+"?page="+page.getAndAdd(1)));
            Elements elements = doc.select("div.recipe.recipe-215-horizontal.pure-g.image-link.display-block");
            isRun=elements.size()>0;
            saveQuestionAndAnswer(elements,0);
        }
        return ReturnT.SUCCESS;
    }

    private void saveQuestionAndAnswer(Elements elements,int i){
        if(i<elements.size()){
            try {
                Question question = new Question();
                Answer answer = new Answer();
                Element element = elements.get(i); // 获取第i个元素

                //标题名
                Element n_element = element.select("p.name").get(0);
                question.setTitle(n_element.text());
                //描述
                Element c_element = element.select("div.cover.pure-u").get(0);
                c_element.select("img").attr("src",c_element.select("img").attr("data-src"));
                c_element.select("img").removeAttr("data-src");
                question.setText(c_element.html());

                //详情链接
                int id = Integer.valueOf(n_element.select("a").attr("href").split("/")[2]);
                question.setQid(id);
                question.setUpdateTime(new Date());
                String infoUrl = url;
                infoUrl=infoUrl.substring(0,infoUrl.lastIndexOf("/",infoUrl.length()-2))+n_element.select("a").attr("href");
                Document doc = Jsoup.parse(HttpCilentUtil.SendGet(infoUrl));
                Element a_element = doc.select("div.block.recipe-show").get(0);
                a_element.select("div.container.pos-r.pb20.has-bottom-border").remove();
                a_element.select("div.rate-dialog.block-negative-margin").remove();
                a_element.select("div.author").remove();
                a_element.select("div.desc.mt30").remove();
                a_element.select("div.print").remove();
                a_element.select("div.ings a").removeAttr("href");
                answer.setQid(id);
                answer.setAid(id);
                answer.setText(a_element.html());
                answer.setCreateTime(new Date());
                questionMapper.insert(question);
                answerMapper.insert(answer);
            }catch (Exception e){
               // e.printStackTrace();
//                XxlJobLogger.log(e);
            }finally {
                i++;
                saveQuestionAndAnswer(elements,i);

            }
        }
    }
}
