package com.shendehaizi.enums;

public enum UserRole {
    Role_Student("1","学生"),
    Role_Teacher("2","老师"),
    Role_Admin("3","管理员");
    public String roleId;
    public String roleName;

    UserRole(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }
}
