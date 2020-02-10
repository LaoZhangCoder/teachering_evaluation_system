package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.RoleNotIncludeException;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.AdminDao;
import com.shendehaizi.dao.StudentDao;
import com.shendehaizi.enums.Code;
import com.shendehaizi.enums.UserRole;
import com.shendehaizi.model.AdminModel;
import com.shendehaizi.model.StudentModel;
import com.shendehaizi.request.LoginRequest;
import com.shendehaizi.response.LoginUserInfo;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.LoginService;
import com.shendehaizi.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AdminDao adminDao;

    @Autowired
    private StudentDao studentDao;

    @Override
    public Response<LoginUserInfo> loginCheck(LoginRequest loginRequest) {
        String roleId = loginRequest.roleId;
        Response<LoginUserInfo> loginResponse = new Response<>();
        Response<String>  checkResponse= new Response<>();
        //校验角色是否存在
        checkRoleInfo(roleId, checkResponse);
        if (roleId.equals(UserRole.Role_Student.roleId)) {
            //登入用户是学生
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("studentName",loginRequest.getUsername());
            StudentModel studentModel = studentDao.findByUniqueIndex(map);
            if(studentModel==null){
                log.error("用户名不存在!");
                throw new ServiceException("用户名不存在!");
            }
            if (!studentModel.getPassword().equals(loginRequest.getPassword())) {
                throw new ServiceException("密码错误!");
            }
            if(!studentModel.getUserId().equals(loginRequest.getUserId())){
                throw new ServiceException("用户id错误!");
            }
            LoginUserInfo loginUserInfo = new LoginUserInfo();
            loginUserInfo.setToken(MD5Util.encrypt_Base64(loginRequest.getUserId()+"_student"));
            loginUserInfo.setRoleId("1");
            loginResponse.setResult(loginUserInfo);
        } else if (roleId.equals(UserRole.Role_Teacher.roleId)) {
            //登入用户是老师
            return null;
        } else {
            //管理员
            HashMap<String, Object> param = Maps.newHashMap();
            param.put("userName", loginRequest.getUsername());
            AdminModel adminInfo = adminDao.findByUniqueIndex(param);
            if (adminInfo == null) {
                loginResponse.setError("用户名不存在!");
                throw new ServiceException("用户名不存在!");
            }
            if (!adminInfo.getAdminPassword().equals(loginRequest.getPassword())) {
                loginResponse.setError("密码错误!");
                throw new ServiceException("密码错误!");
            }
        if(!adminInfo.getAdminId().equals(loginRequest.getUserId())){
            loginResponse.setError("用户id错误!");
            throw new ServiceException("用户id错误!");
        }
            LoginUserInfo loginUserInfo = new LoginUserInfo();
            loginUserInfo.setToken(MD5Util.encrypt_Base64(loginRequest.getUserId()+"_admin"));
            loginUserInfo.setRoleId("3");
            loginResponse.setResult(loginUserInfo);
        }
        return  loginResponse;

    }

    private void checkRoleInfo(String roleId, Response<String> loginResponse) {
        if (!(roleId.equals(UserRole.Role_Student.getRoleId())
                || roleId.equals(UserRole.Role_Teacher.getRoleId())
                || roleId.equals(UserRole.Role_Admin.getRoleId()))) {
            loginResponse.setError("角色不存在异常!");
            throw new RoleNotIncludeException("角色不存在异常!");
        }
    }
}
