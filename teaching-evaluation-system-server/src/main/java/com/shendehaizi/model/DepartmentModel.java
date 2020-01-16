package com.shendehaizi.model;

import lombok.Data;

import java.util.Date;
@Data
public class DepartmentModel {

    private Integer id;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 更新时间
     */
    private Date updateAt;
}
