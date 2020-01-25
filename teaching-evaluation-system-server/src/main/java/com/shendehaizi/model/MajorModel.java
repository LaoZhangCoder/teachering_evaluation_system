package com.shendehaizi.model;

import lombok.Data;

import java.util.Date;

@Data
public class MajorModel {
    private  Integer id;
    private  String majorName;
    private Date createDate;
    private Date updateDate;

}
