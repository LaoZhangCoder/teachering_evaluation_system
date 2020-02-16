package com.shendehaizi.request;

import lombok.Data;

@Data
public class ScoreRequest {
    private Long countScore;
    private String message;
    private Long courseId;
    private Long teacherId;
    private String studentId;
}
