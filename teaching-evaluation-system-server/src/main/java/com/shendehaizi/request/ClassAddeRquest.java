package com.shendehaizi.request;

import lombok.Data;

@Data
public class ClassAddeRquest {
    /**
     * 班级名称
     */
    private String name;

    /**
     * 院系id
     */
    private Integer departmentId;
    /**
     * 专业id
     */
    private Integer majorId;

}
