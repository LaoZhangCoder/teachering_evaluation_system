package com.shendehaizi.request;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
