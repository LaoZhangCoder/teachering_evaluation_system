package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.UserActivationDao;
import com.shendehaizi.model.UserActivationModel;
import com.shendehaizi.request.UserActivationRequest;
import com.shendehaizi.response.ActivationUserInfo;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.UserActivationService;
import io.terminus.common.model.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Response<List<ActivationUserInfo>> getActivationUserInfo() {
        Response<List<ActivationUserInfo>> listResponse = new Response<>();
        Paging<UserActivationModel> paging = userActivationDao.paging(0, 8);
        List<UserActivationModel> userActivationModels = paging.getData();
        listResponse.setCount(paging.getTotal());
        if(userActivationModels!=null){
            List<ActivationUserInfo> collect = userActivationModels.stream().map(userActivationModel -> {
                ActivationUserInfo activationUserInfo = new ActivationUserInfo();
                activationUserInfo.setUserId(userActivationModel.getUserId());
                if (userActivationModel.getRoleId().equals(1)) {
                    activationUserInfo.setRoleName("学生");
                }
                if (userActivationModel.getRoleId().equals(2)) {
                    activationUserInfo.setRoleName("老师");
                }
                if (userActivationModel.getStatus().equals(0)) {
                    activationUserInfo.setRegister("未注册");
                } else {
                    activationUserInfo.setRegister("已注册");
                }
                return activationUserInfo;
            }).collect(Collectors.toList());
            listResponse.setResult(collect);
        }
       return listResponse;
    }

    @Override
    public Response<List<ActivationUserInfo>> getPagingActivationUserInfo(Integer currentPage,Integer pageSize) {
        int offset=(currentPage-1)*pageSize;
        Paging<UserActivationModel> paging = userActivationDao.paging(offset, pageSize);
        Response<List<ActivationUserInfo>> listResponse = new Response<>();
        listResponse.setCount(paging.getTotal());
      if(!paging.getData().isEmpty()){
          List<ActivationUserInfo> collect = paging.getData().stream().map(userActivationModel -> {
              ActivationUserInfo activationUserInfo = new ActivationUserInfo();
              activationUserInfo.setUserId(userActivationModel.getUserId());
              if (userActivationModel.getRoleId().equals(1)) {
                  activationUserInfo.setRoleName("学生");
              }
              if (userActivationModel.getRoleId().equals(2)) {
                  activationUserInfo.setRoleName("老师");
              }
              if (userActivationModel.getStatus().equals(0)) {
                  activationUserInfo.setRegister("未注册");
              } else {
                  activationUserInfo.setRegister("已注册");
              }
              return activationUserInfo;
          }).collect(Collectors.toList());
          listResponse.setResult(collect);
      }
        return listResponse;
    }
}
