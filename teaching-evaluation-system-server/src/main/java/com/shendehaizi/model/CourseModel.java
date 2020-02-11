package com.shendehaizi.model;

import lombok.Data;

import java.util.Date;

@Data
public class CourseModel {
    /**
     * id
     */
    private Long id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 专业id
     */
    private  Long majorId;

    /**
     * 班级id
     */
    private Long classId;

    private Date createDate;

    private Date updateDate;
}
