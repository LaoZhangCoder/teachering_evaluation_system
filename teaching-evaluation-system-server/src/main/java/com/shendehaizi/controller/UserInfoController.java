package com.shendehaizi.controller;

import com.shendehaizi.enums.Code;
import com.shendehaizi.request.UserRegisterRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.response.UserInfo;
import com.shendehaizi.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/user/info")
    public Response getToken(String token) {
        if(StringUtils.isEmpty(token)) return null;
        UserInfo userInfo = userInfoService.getUserInfo(token);
        Response<UserInfo> userInfoResponse = new Response<>();
        userInfoResponse.setCode(Code.SUCCESS.getStatus());
        userInfoResponse.setResult(userInfo);
        return  userInfoResponse;
    }

    @PostMapping(value = "/user/register")
    public Response<String>  userRegister(@RequestBody UserRegisterRequest request){
        return userInfoService.handleStudentRegister(request);
    }

    @GetMapping(value = "/user/sign-out")
    public Response<String> logout(){
        Response<String> response = new Response<>();
        response.setResult("退出登录成功!");
        return  response;
    }
}
