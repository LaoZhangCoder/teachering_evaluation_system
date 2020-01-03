package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.RoleNotIncludeException;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.AdminDao;
import com.shendehaizi.enums.Code;
import com.shendehaizi.enums.UserRole;
import com.shendehaizi.model.AdminModel;
import com.shendehaizi.request.LoginRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.LoginService;
import com.shendehaizi.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AdminDao adminDao;

    @Override
    public Response<String> loginCheck(LoginRequest loginRequest) {
        String roleId = loginRequest.roleId;
        Response<String> loginResponse = new Response<>();
        //校验角色是否存在
        checkroleInfo(roleId, loginResponse);
        if (roleId.equals(UserRole.Role_Student.roleId)) {
            //登入用户是学生
            return null;
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
        loginResponse.setResult(MD5Util.encrypt_Base64(loginRequest.getUserId()+"_admin"));
        loginResponse.setCode(Code.SUCCESS.getStatus());
        return loginResponse;
        }

    }

    private void checkroleInfo(String roleId, Response<String> loginResponse) {
        if (roleId.equals(UserRole.Role_Student.roleId)
                || roleId.equals(roleId.equals(UserRole.Role_Teacher.roleId)
                || roleId.equals(UserRole.Role_Admin.roleId))) {
            loginResponse.setError("角色不存在异常!");
            throw new RoleNotIncludeException("角色不存在异常!");
        }
    }
}
