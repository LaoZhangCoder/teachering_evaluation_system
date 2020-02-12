package com.shendehaizi.response;

import lombok.Data;

@Data
public class TeacherCourseInfo {
    /**
     * 教师编号
     */
    private String teacherId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     *所属院系
     */
    private String departmentName;

    /**
     * 所属专业
     */
    private String majorName;

    /**
     * 所属班级
     */
    private String className;

    private Long id;


}
