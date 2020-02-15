package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.AdminDao;
import com.shendehaizi.dao.StudentDao;
import com.shendehaizi.dao.TeacherDao;
import com.shendehaizi.model.*;
import com.shendehaizi.model.AdminModel;
import com.shendehaizi.model.AdminModel;
import com.shendehaizi.model.AdminModel;
import com.shendehaizi.request.AdminAddRequest;
import com.shendehaizi.request.AdminUpdateRequest;
import com.shendehaizi.request.UserInfoRequest;
import com.shendehaizi.response.*;
import com.shendehaizi.response.AdminInfo;
import com.shendehaizi.response.AdminInfo;
import com.shendehaizi.service.AdminActivationService;
import io.terminus.common.model.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminActivationServiceImpl implements AdminActivationService {
    @Autowired
    private AdminDao adminDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeacherDao teacherDao;

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
        map.put("adminId", id);
        AdminModel AdminModel = adminDao.findByUniqueIndex(map);
        if (AdminModel == null) {
            log.error("删除的对象不存在！");
            throw new ServiceException("删除的对象不存在!");
        }
        adminDao.delete(AdminModel.getId());
        Response<String> response = new Response<>();
        response.setResult("删除成功!");
        return response;
    }

    @Override
    public Response<List<UserInfoDetail>> getUserInfos(UserInfoRequest request) {
        Response<List<UserInfoDetail>> listResponse = new Response<>();
        List<StudentModel> studentModels = studentDao.listAll();
        List<TeacherModel> teacherModels = teacherDao.listAll();
        List<UserInfoDetail> studentList = studentModels.stream().map(studentModel -> {
            UserInfoDetail userInfoDetail = new UserInfoDetail();
            userInfoDetail.setDate(studentModel.getCreateDate());
            userInfoDetail.setId(studentModel.getId());
            userInfoDetail.setRoleName("学生");
            userInfoDetail.setUserName(studentModel.getStudentName());
            userInfoDetail.setUserId(studentModel.getUserId());
            return userInfoDetail;
        }).collect(Collectors.toList());
        List<UserInfoDetail> teacherList = teacherModels.stream().map(teacherModel -> {
            UserInfoDetail userInfoDetail = new UserInfoDetail();
            userInfoDetail.setDate(teacherModel.getCreateDate());
            userInfoDetail.setId(teacherModel.getId());
            userInfoDetail.setRoleName("教师");
            userInfoDetail.setUserName(teacherModel.getTeacherName());
            userInfoDetail.setUserId(teacherModel.getUserId());
            return userInfoDetail;
        }).collect(Collectors.toList());
        studentList.addAll(teacherList);
        List<UserInfoDetail> collect = studentList;
        collect = studentList.stream().filter(
                filter(request)
        ).skip((request.getPage() - 1) * request.getLimit()).limit(request.getLimit()).collect(Collectors.toList());
        if (StringUtils.isEmpty(request.getUserName())) {
            listResponse.setCount(Long.valueOf(studentList.size()));
        } else {
            listResponse.setCount(Long.valueOf(collect.size()));
        }
        listResponse.setResult(collect);
        return listResponse;
    }

    private Predicate<UserInfoDetail> filter(UserInfoRequest request) {
        return (UserInfoDetail userInfoDetail) -> {
            if (StringUtils.isEmpty(request.getUserName())) return true;
            return userInfoDetail.getUserName().equals(request.getUserName());
        };
    }
}
