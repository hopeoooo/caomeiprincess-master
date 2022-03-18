package com.xxl.job.executor.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@Table(name = "KW_QUESTION")
public class Question {
    //问题ID
    @Column(name = "QID")
    private  long qid;

    //问题标题
    @Column(name = "TITLE")
    private  String title;

    //问题描述
    @Column(name = "TEXT")
    private  String text;

    //修改时间
    @Column(name = "UPDATETIME")
    private Date updateTime;

}
