package com.shendehaizi.response;

import lombok.Data;

import java.util.Date;

@Data
public class ClassInfo {
    private Date date;
    private String majorName;
    private String departmentName;
    private String className;
    private Integer majorId;
    private Integer departmentId;
    private Integer classId;
}

