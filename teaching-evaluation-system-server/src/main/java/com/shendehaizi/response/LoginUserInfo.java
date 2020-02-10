package com.shendehaizi.response;

import lombok.Data;

@Data
public class LoginUserInfo {
    private String token;

    private String roleId;
}
