package com.shendehaizi.model;



import java.util.Date;


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

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
