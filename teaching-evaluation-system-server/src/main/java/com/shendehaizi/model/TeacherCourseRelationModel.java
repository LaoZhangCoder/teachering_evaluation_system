package com.shendehaizi.model;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherCourseRelationModel {
    private Long id;

    private Long classId;

    private Long majorId;

    private Long departmentId;

    private Date createDate;

    private Date updateDate;

    private Long courseId;

    private String userId;
}
