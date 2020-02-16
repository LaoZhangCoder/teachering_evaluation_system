package com.shendehaizi.request;

import lombok.Data;

@Data
public class StudentScoreRequest {
    private Long countScore;
    private String message;
    private String courseId;
    private String teacherId;
    private String studentId;
}
