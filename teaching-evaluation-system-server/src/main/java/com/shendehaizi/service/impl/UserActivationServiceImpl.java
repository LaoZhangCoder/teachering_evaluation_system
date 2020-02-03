package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.UserActivationDao;
import com.shendehaizi.model.UserActivationModel;
import com.shendehaizi.request.UserActivationRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.UserActivationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@Slf4j
public class UserActivationServiceImpl implements UserActivationService {
    @Autowired
    private UserActivationDao userActivationDao;
    @Override
    public Response<String> addUserActivation(UserActivationRequest request) {
        Response<String> response = new Response<>();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userId",request.getUserId());
        UserActivationModel userActivationModel = userActivationDao.findByUniqueIndex(map);
        if(userActivationModel!=null){
            log.error("已存在该用户编号！");
            throw new ServiceException("已存在该用户编号!");
        }
        userActivationModel=new UserActivationModel();
        userActivationModel.setCreateDate(new Date());
        userActivationModel.setId(null);
        userActivationModel.setRoleId(request.getRoleId());
        userActivationModel.setStatus(0);
        userActivationModel.setUpdateDate(new Date());
        userActivationModel.setUserId(request.getUserId());
        userActivationDao.create(userActivationModel);
        response.setResult("添加成功！");
        response.setCode(200);
        return response;
    }
}
