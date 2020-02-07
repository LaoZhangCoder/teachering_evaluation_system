package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.AdminDao;
import com.shendehaizi.model.*;
import com.shendehaizi.model.AdminModel;
import com.shendehaizi.model.AdminModel;
import com.shendehaizi.model.AdminModel;
import com.shendehaizi.request.AdminAddRequest;
import com.shendehaizi.request.AdminUpdateRequest;
import com.shendehaizi.response.AdminInfo;
import com.shendehaizi.response.AdminInfo;
import com.shendehaizi.response.AdminInfo;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.AdminActivationService;
import io.terminus.common.model.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@Slf4j
public class AdminActivationServiceImpl implements AdminActivationService {
    @Autowired
    private AdminDao adminDao;
    @Override
    public Response<List<AdminInfo>> getAdminInfo() {
        Response<List<AdminInfo>> listResponse = new Response<>();
        Paging<AdminModel> paging = adminDao.paging(0, 8);
        List<AdminModel> AdminModels = paging.getData();
        listResponse.setCount(paging.getTotal());
        if (AdminModels != null) {
            List<AdminInfo> collect = AdminModels.stream().map(AdminModel -> {
                AdminInfo AdminInfo = new AdminInfo();
              AdminInfo.setAdminId(AdminModel.getAdminId());
              AdminInfo.setAdminName(AdminModel.getAdminName());
                return AdminInfo;
            }).collect(Collectors.toList());
            listResponse.setResult(collect);
        }
        return listResponse;
    }

    @Override
    public Response<String> addAdmin(AdminAddRequest request) {
        Response<String> response = new Response<>();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userName", request.getAdminName());
        AdminModel AdminModel = adminDao.findByUniqueIndex(map);
        if (AdminModel != null) {
            log.error("已存在该管理员名称！");
            throw new ServiceException("已存在该管理员名称,请重新命名!");
        }
        AdminModel = new AdminModel();
        AdminModel.setCreatedAt(new Date());
        AdminModel.setRoleId(3);
        AdminModel.setAdminId(UUID.randomUUID().toString());
        AdminModel.setUpdatedAt(new Date());
        AdminModel.setAdminName(request.getAdminName());
        AdminModel.setAdminPassword(request.getAdminPassword());
        adminDao.create(AdminModel);
        response.setResult("添加成功！");
        response.setCode(200);
        return response;
    }

    @Override
    public Response<List<AdminInfo>> getPagingAdminInfo(Integer currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        Paging<AdminModel> paging = adminDao.paging(offset, pageSize);
        Response<List<AdminInfo>> listResponse = new Response<>();
        listResponse.setCount(paging.getTotal());
        if (!paging.getData().isEmpty()) {
            List<AdminInfo> collect = paging.getData().stream().map(AdminModel -> {
                AdminInfo AdminInfo = new AdminInfo();
                AdminInfo.setAdminId(AdminModel.getAdminId());
                AdminInfo.setAdminName(AdminModel.getAdminName());
                return AdminInfo;
            }).collect(Collectors.toList());
            listResponse.setResult(collect);
        }
        return listResponse;
    }

    @Override
    public Response<String> updateAdminInfo(AdminUpdateRequest request, String id) {
        Response<String> response = new Response<>();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userName", request.getAdminName());
        AdminModel AdminModel = adminDao.findByUniqueIndex(map);
        map.put("userName", id);
        AdminModel oldUser = adminDao.findByUniqueIndex(map);
        if (oldUser == null) {
            log.error("发生错误，修改的数据不存在!");
            throw new ServiceException("发生错误，修改的数据不存在!");
        }
        if (AdminModel != null && !oldUser.getId().equals(AdminModel.getId())) {
            log.error("修改的用户id已存在请检测是否重复!");
            throw new ServiceException("修改的用户id已存在请检测是否重复!");
        }
        oldUser.setAdminName(request.getAdminName());
        oldUser.setUpdatedAt(new Date());
        oldUser.setAdminPassword(request.getAdminPassword());
        adminDao.update(oldUser);
        response.setResult("修改成功");
        return response;
    }

    @Override
    public Response<String> deleteAdminInfo(String id) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("adminId",id);
        AdminModel AdminModel = adminDao.findByUniqueIndex(map);
        if(AdminModel==null){
            log.error("删除的对象不存在！");
            throw new ServiceException("删除的对象不存在!");
        }
        adminDao.delete(AdminModel.getId());
        Response<String> response = new Response<>();
        response.setResult("删除成功!");
        return response;
    }
}
