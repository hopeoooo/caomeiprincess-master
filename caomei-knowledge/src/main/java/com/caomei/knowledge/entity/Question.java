package com.caomei.knowledge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class Question {
    private int qId;
    private int aId;
    private String title;
    private String qText;
    private String aText;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date qUpdateDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date aCreateDate;

    public Question(int qId, int aId, String title, String qText, String aText, Date qUpdateDate, Date aCreateDate) {
        this.qId = qId;
        this.aId = aId;
        this.title = title;
        this.qText = qText;
        this.aText = aText;
        this.qUpdateDate = qUpdateDate;
        this.aCreateDate = aCreateDate;
    }

    public Question() {
    }
}
