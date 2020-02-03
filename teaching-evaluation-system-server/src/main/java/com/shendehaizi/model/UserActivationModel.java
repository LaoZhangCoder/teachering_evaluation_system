package com.shendehaizi.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserActivationModel {
    /**
     * id
     */
    private Long  id;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 角色id
     */

    private Integer roleId;

    /**
     * 激活状态 0未激活 1 激活
     */
    private Integer status;

    private Date createDate;

    private Date updateDate;
}
