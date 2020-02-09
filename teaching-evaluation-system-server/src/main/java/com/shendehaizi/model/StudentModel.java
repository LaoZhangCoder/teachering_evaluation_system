package com.shendehaizi.model;

import lombok.Data;

import java.util.Date;

@Data
public class StudentModel {
    /**
     * id
     */
    private Long id;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 密码
     */
    private String password;

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 专业id
     */
    private Long majorId;

    /**
     * 班级id
     */
    private Long classId;

    /**
     * 用户id
     */
    private String userId;

    private Date createDate;

    private Date updateDate;
}
