package com.shendehaizi.request;

import lombok.Data;

@Data
public class LoginRequest {
    /**
     * 用户名
     */
    public String username;
    /***
     *用户id
     */
    public String userId;
    /**
     * 用户密码
     */
    public String password;

    /**
     * 角色
     */
    public String roleId;

}
