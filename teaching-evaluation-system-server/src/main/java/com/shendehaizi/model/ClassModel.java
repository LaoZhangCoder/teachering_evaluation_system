package com.shendehaizi.model;

import lombok.Data;

import java.util.Date;

@Data
public class ClassModel {
    /**
     * id
     */
    private Integer id;
    /**
     * 班级名称
     */
    private String  className;

    private Date createDate;

    private Date updateDate;
}
