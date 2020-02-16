package com.shendehaizi.response;

import lombok.Data;

import java.util.Date;

@Data
public class StudentScoreRecord {
    private Long id;
    private String message;
    private String teacherName;
    private String courseName;
    private Date date;
}
