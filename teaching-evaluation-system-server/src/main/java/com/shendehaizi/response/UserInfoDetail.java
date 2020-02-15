package com.shendehaizi.response;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoDetail {
    private Long id;
    private Date date;
    private String userName;
    private String userId;
    private String roleName;

}
