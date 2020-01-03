package com.shendehaizi.model;

import lombok.Data;

import java.util.Date;

@Data
public class AdminModel {
    /**
     * 管理员姓名
     */
   private String adminName;
    /**
     * 管理员id
     */
   private String adminId;
    /**
     * 管理员密码
     */
   private String adminPassword;
    /**
     * 最近更新时间
     */
   private Date updatedAt;
    /**
     * 创建时间
     */
   private Date createAt;

    /**
     * id
     */
    private Long id;

    private Integer roleId;
}
