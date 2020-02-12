package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.*;
import com.shendehaizi.model.*;
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

    @Autowired
    private TeacherDao teacherDao;


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
        }else if(userRole.equals("student")){
            param.put("userId",userId);
            StudentModel studentModel = studentDao.findByUniqueIndex(param);
            if(studentModel==null){
                log.error("用户信息获取失败!");
                throw new ServiceException("用户信息获取失败!");
            }
            Map<String,Integer> roleParam=Maps.newHashMap();
            roleParam.put("id",1);
            RoleModel roleInfo = roleDao.getRoleInfo(roleParam);
            if(roleInfo!=null) {
                userInfo.setRoleId(roleInfo.getId());
                userInfo.setRoleName(roleInfo.getRoleName());
                userInfo.setUserId(studentModel.getUserId());
                userInfo.setUserName(studentModel.getStudentName());
            }
            return userInfo;
        }else {
            param.put("userId",userId);
            TeacherModel teacherModel = teacherDao.findByUniqueIndex(param);
            if(teacherModel==null){
                log.error("用户信息获取失败!");
                throw new ServiceException("用户信息获取失败!");
            }
            Map<String,Integer> roleParam=Maps.newHashMap();
            roleParam.put("id",2);
            RoleModel roleInfo = roleDao.getRoleInfo(roleParam);
            if(roleInfo!=null) {
                userInfo.setRoleId(roleInfo.getId());
                userInfo.setRoleName(roleInfo.getRoleName());
                userInfo.setUserId(teacherModel.getUserId());
                userInfo.setUserName(teacherModel.getTeacherName());
            }
            return userInfo;
        }

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
        if(isStudent(request)) {
            StudentModel studentModel = new StudentModel();
            BeanUtils.copyProperties(request, studentModel);
            studentModel.setStudentName(request.getUserName());
            studentModel.setUpdateDate(new Date());
            studentModel.setCreateDate(new Date());
            Boolean aBoolean = studentDao.create(studentModel);
            if (aBoolean) {
                activationModel.setStatus(1);
                userActivationDao.update(activationModel);
                response.setResult("注册成功");
            } else {
                response.setError("注册失败");
            }
        }else{
            TeacherModel teacherModel = new TeacherModel();
            teacherModel.setCreateDate(new Date());
            teacherModel.setPassword(request.getPassword());
            teacherModel.setTeacherName(request.getUserName());
            teacherModel.setUpdateDate(new Date());
            teacherModel.setUserId(request.getUserId());
            Boolean aBoolean = teacherDao.create(teacherModel);
            if (aBoolean) {
                activationModel.setStatus(1);
                userActivationDao.update(activationModel);
                response.setResult("注册成功");
            } else {
                response.setError("注册失败");
            }
        }
        return  response;
    }

    private boolean isStudent(UserRegisterRequest request) {
        return request.getRoleId().equals(1);
    }
}
