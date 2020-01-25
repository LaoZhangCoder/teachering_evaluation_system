package com.shendehaizi.request;

import lombok.Data;

@Data
public class DepartmentUpdateRequest {

    private String newDepartmentName;

    private String oldDepartmentName;
}
