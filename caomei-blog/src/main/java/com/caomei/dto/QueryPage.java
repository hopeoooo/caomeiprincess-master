package com.caomei.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class QueryPage implements Serializable {

    private int pageCode;
    private int pageSize;
}
