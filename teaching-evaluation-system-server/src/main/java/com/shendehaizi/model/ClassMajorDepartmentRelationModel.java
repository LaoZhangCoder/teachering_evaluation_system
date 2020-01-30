package com.shendehaizi.model;

import lombok.Data;

import java.util.Date;

@Data
public class ClassMajorDepartmentRelationModel {
    private Integer id;

    private Integer classId;

    private Integer majorId;

    private Integer departmentId;

    private Date createDate;

    private Date updateDate;
}
