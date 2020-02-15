package com.shendehaizi.request;

import lombok.Data;

@Data
public class UserInfoRequest {
    private String userName;
    private Integer  page;
    private Integer limit;
}
