package com.shendehaizi.response;

import lombok.Data;

import java.util.Date;

@Data
public class MajorInfo {
    private Date date;
    private String majorName;
    private String departmentName;
    private Integer majorId;
    private Integer departmentId;
}
