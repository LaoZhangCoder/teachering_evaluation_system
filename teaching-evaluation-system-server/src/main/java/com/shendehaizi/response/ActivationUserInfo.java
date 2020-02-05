package com.shendehaizi.response;

import lombok.Data;

@Data
public class ActivationUserInfo {
    /**
     * 用户编号
     */
    private String userId;

    /**
     *用户角色
     */
    private String roleName;

    /**
     * 是否注册
     */
    private String register;
}
