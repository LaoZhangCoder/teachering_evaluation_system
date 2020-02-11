package com.shendehaizi.request;

import lombok.Data;

@Data
public class StudentUpdateRequest {
    public String name;

    private String password;

    private Long departmentId;

    private Long majorId;

    private Long classId;

    private String userId;
}
