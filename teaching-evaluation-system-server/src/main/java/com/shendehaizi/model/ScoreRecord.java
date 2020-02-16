package com.shendehaizi.model;

import lombok.Data;

import java.util.Date;

@Data
public class ScoreRecord {
    private Long id;
    private Long courseId;
    private Long teacherId;
    private Long studentId;
    private Long score;
    private String message;
    private Date createDate;
    private Date updateDate;
}
