package com.xxl.job.executor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@Table(name = "KW_ANSWER")
public class Answer {
    //回答主键ID
    @Column(name="AID")
    private  long aid;

    @Column(name="text")
    private  String text;

    //回答的问题ID
    @Column(name="QID")
    private  long qid;

    //创建时间
    @Column(name="CREATETIME")
    private Date createTime;

}
