package com.shendehaizi.controller;

import com.shendehaizi.enums.Code;
import com.shendehaizi.response.Response;
import com.shendehaizi.response.UserInfo;
import com.shendehaizi.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/user/info")
    public Response getToken(String token) {
        UserInfo userInfo = userInfoService.getUserInfo(token);
        Response<UserInfo> userInfoResponse = new Response<>();
        userInfoResponse.setCode(Code.SUCCESS.getStatus());
        userInfoResponse.setResult(userInfo);
        return  userInfoResponse;
    }
}
