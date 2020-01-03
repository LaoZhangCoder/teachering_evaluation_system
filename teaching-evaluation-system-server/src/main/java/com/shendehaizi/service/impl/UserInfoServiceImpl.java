package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.AdminDao;
import com.shendehaizi.dao.RoleDao;
import com.shendehaizi.model.AdminModel;
import com.shendehaizi.model.RoleModel;
import com.shendehaizi.response.UserInfo;
import com.shendehaizi.service.UserInfoService;
import com.shendehaizi.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private RoleDao roleDao;
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
}
