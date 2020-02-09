package com.shendehaizi.service;

import com.shendehaizi.request.UserRegisterRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.response.UserInfo;

public interface UserInfoService {
     UserInfo getUserInfo(String token);

    Response<String> handleStudentRegister(UserRegisterRequest request);
}
