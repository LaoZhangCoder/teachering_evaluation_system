package com.shendehaizi.request;

import lombok.Data;

@Data
public class UserActivationUpdateRequest {

    private String userId;

    private String roleName;

    private String register;

    private Integer roleId;
}
