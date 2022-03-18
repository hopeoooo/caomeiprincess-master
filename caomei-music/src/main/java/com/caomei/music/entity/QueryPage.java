package com.caomei.music.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class QueryPage implements Serializable {

    private int pageCode;
    private int pageSize;
}
