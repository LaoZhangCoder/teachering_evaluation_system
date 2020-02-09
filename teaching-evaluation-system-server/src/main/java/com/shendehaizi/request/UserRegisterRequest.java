package com.shendehaizi.request;

import lombok.Data;

import java.util.List;

@Data
public class UserRegisterRequest {
    /**
     * 角色
     */
    private Integer roleId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户id
     */

    private String userId;

    /**
     * 用户密码
     */

    private String password;

    /**
     * 院系id
     */

    private Long departmentId;

    /***
     * 专业id
     */

    private  Long majorId;

    /**
     * 班级id
     */

    private Long classId;
}
