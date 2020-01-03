package com.shendehaizi.response;

import lombok.Data;

@Data
public class UserInfo {

    /**
     * 管理员姓名
     */
    private String userName;
    /**
     * 管理员id
     */
    private String userId;
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色姓名
     */
    private String roleName;
}
