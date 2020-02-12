package com.shendehaizi.request;

import lombok.Data;

@Data
public class TeacherCourseAddRequest {
    private String userId;

    private Long departmentId;

    private Long majorId;

    private  Long classId;

    private Long courseId;
}
