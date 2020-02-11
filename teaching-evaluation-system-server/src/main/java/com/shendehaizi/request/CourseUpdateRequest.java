package com.shendehaizi.request;

import lombok.Data;

@Data
public class CourseUpdateRequest {


    private String courseName;

    private Long departmentId;

    private Long majorId;

    private Long classId;
}
