package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.AdminDao;
import com.shendehaizi.dao.RoleDao;
import com.shendehaizi.dao.StudentDao;
import com.shendehaizi.dao.UserActivationDao;
import com.shendehaizi.model.AdminModel;
import com.shendehaizi.model.RoleModel;
import com.shendehaizi.model.StudentModel;
import com.shendehaizi.model.UserActivationModel;
import com.shendehaizi.request.UserRegisterRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.response.UserInfo;
import com.shendehaizi.service.UserInfoService;
import com.shendehaizi.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserActivationDao userActivationDao;

    @Autowired
    private StudentDao studentDao;

    @Override
    public UserInfo getUserInfo(String token) {
        UserInfo userInfo = new UserInfo();
        Map<String, Object> param= Maps.newHashMapWithExpectedSize(1);
        String s = MD5Util.decrypt_Base64(token);
        String userId = s.substring(0, s.indexOf("_"));
        String userRole=s.substring(s.indexOf("_")+1);
        if(userRole.equals("admin")){
            param.put("adminId",userId);
            AdminModel adminInfo = adminDao.findByUniqueIndex(param);
            if(adminInfo==null){
                log.error("用户信息获取失败!");
              throw new ServiceException("用户信息获取失败!");
            }
            Map<String,Integer> roleParam=Maps.newHashMap();
            roleParam.put("id",adminInfo.getRoleId());
            RoleModel roleInfo = roleDao.getRoleInfo(roleParam);
            if(roleInfo!=null) {
                userInfo.setRoleId(roleInfo.getId());
                userInfo.setRoleName(roleInfo.getRoleName());
                userInfo.setUserId(adminInfo.getAdminId());
            }
            return userInfo;
        }
        return null;
    }

    @Override
    public Response<String> handleStudentRegister(UserRegisterRequest request) {
        Response<String> response = new Response<>();
        String userId = request.getUserId();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userId",userId);
        UserActivationModel activationModel = userActivationDao.findByUniqueIndex(map);
        if(activationModel==null){
            log.error("该用户未被激活!请联系管理员");
            throw  new ServiceException("该用户未被激活!请联系管理员");
        }
        if(activationModel.getStatus().equals(1)){
            throw  new ServiceException("该用户已被注册");
        }
        StudentModel studentModel = new StudentModel();
        BeanUtils.copyProperties(request,studentModel);
        studentModel.setStudentName(request.getUserName());
        studentModel.setUpdateDate(new Date());
        studentModel.setCreateDate(new Date());
        Boolean aBoolean = studentDao.create(studentModel);
        if(aBoolean){
            activationModel.setStatus(1);
            userActivationDao.update(activationModel);
            response.setResult("注册成功");
        }else{
            response.setError("注册失败");
        }
        return  response;
    }
}
