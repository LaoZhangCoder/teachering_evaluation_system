package com.shendehaizi.service;


import com.shendehaizi.request.AdminAddRequest;
import com.shendehaizi.request.AdminUpdateRequest;
import com.shendehaizi.request.UserInfoRequest;
import com.shendehaizi.response.*;

import java.util.List;

public interface AdminActivationService {

    Response<List<AdminInfo>> getAdminInfo();

    Response<String> addAdmin(AdminAddRequest request);

    Response<List<AdminInfo>> getPagingAdminInfo(Integer currentPage, int i);

    Response<String> updateAdminInfo(AdminUpdateRequest request, String id);

    Response<String> deleteAdminInfo(String id);

    Response<List<UserInfoDetail>> getUserInfos(UserInfoRequest request);

    Response<CountInfo> getCountInfos();

    Response<ProcessInfo> getProcessInfo();
}
