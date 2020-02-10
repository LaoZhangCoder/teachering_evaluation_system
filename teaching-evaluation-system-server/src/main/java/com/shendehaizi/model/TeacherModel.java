package com.shendehaizi.model;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherModel {
    /**
     * id
     */
    private Long id;
    /**
     * 用户编号
     */
    private String userId;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 密码
     */
    private String password;

    private Date createDate;

    private Date updateDate;
}
