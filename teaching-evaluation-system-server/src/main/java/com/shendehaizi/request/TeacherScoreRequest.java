package com.shendehaizi.request;

import lombok.Data;

@Data
public class TeacherScoreRequest {
    private String teacherName;
    private Long page;
    private Long limit;
}
