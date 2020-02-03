package com.shendehaizi.service;

import com.shendehaizi.request.UserActivationRequest;
import com.shendehaizi.response.Response;

public interface UserActivationService {
    Response<String> addUserActivation(UserActivationRequest request);
}
