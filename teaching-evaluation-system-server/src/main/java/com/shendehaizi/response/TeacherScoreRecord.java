package com.shendehaizi.response;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherScoreRecord {
    private Long id;
    private String courseName;
    private Long score;
    private String message;
    private Date date;
}
