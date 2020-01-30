package com.shendehaizi.request;

import lombok.Data;

@Data
public class ClassUpdateRequest {
    /**
     * 旧的班级名
     */
    private String oldName;
    /**
     * 新的班级名
     */
    private  String name;

    private Long classId;

    private  Long departmentId;

    private  Long majorId;

}
