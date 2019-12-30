package com.shendehaizi.service;

import com.shendehaizi.request.LoginRequest;
import com.shendehaizi.response.Response;

public interface LoginService {
    Response<String> loginCheck(LoginRequest loginRequest);
}
