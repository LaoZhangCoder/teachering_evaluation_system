package com.shendehaizi.request;

import lombok.Data;

@Data
public class MajorUpdateRequest {
    /**
     * 专业名称
     */
    private String name;

    /**
     * 院系id
     */
    private Integer departmentId;

    /**
     * 旧的院系id
     */
    private Integer oldDepartmentId;
    /**
     * 专业id
     */
    private Integer majorId;

}
