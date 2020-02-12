package com.shendehaizi.request;

import lombok.Data;

@Data
public class TeacherUpdateRequest {
    private String name;

    private String password;

    private String userId;
}
