package com.shendehaizi.service;

import com.shendehaizi.request.UserActivationRequest;
import com.shendehaizi.response.ActivationUserInfo;
import com.shendehaizi.response.Response;

import java.util.List;

public interface UserActivationService {
    Response<String> addUserActivation(UserActivationRequest request);

    Response<List<ActivationUserInfo>> getActivationUserInfo();

    Response<List<ActivationUserInfo>> getPagingActivationUserInfo(Integer currentPage,Integer pageSize);
}
