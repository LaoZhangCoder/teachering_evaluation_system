package com.shendehaizi.request;

import lombok.Data;

@Data
public class UserActivationRequest {

    private String userId;

    private Integer roleId;
}
