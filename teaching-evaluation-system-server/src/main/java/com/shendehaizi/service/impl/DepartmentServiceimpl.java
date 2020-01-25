package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.DeparmentDao;
import com.shendehaizi.enums.Code;
import com.shendehaizi.model.DepartmentModel;
import com.shendehaizi.request.DeparmentRequest;
import com.shendehaizi.request.DepartmentUpdateRequest;
import com.shendehaizi.response.DepartmentInfo;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceimpl implements DepartmentService {
    @Autowired
    private DeparmentDao deparmentDao;
    @Override
    public Response<Object> addDepartment(DeparmentRequest deparmentRequest) {
        Response<Object> loginResponse=new Response<>();
        String departmentName=deparmentRequest.getDepartmentName();
        if(StringUtils.isEmpty(departmentName)){
            loginResponse.setError("院系名称不能为空!");
            throw new ServiceException("院系名称不能为空!");
        }
        //是否存在相同名称的部门
        isExcludeDeparment(departmentName, loginResponse);
        DepartmentModel departmentModel = new DepartmentModel();
        departmentModel.setDepartmentName(departmentName);
        departmentModel.setCreateAt(new Date());
        departmentModel.setUpdateAt(new Date());
        Boolean isSuccess = deparmentDao.addDeparment(departmentModel);
        loginResponse.setSuccess(isSuccess);
        loginResponse.setCode(Code.SUCCESS.getStatus());
        loginResponse.setResult("添加成功");
        return loginResponse;
    }

    @Override
    public Response<Object> deleteDepartment(DeparmentRequest request) {
        Response<Object> deleteResult = new Response<>();
        Map<String, Object> param=new HashMap<>();
        param.put("departmentName",request.getDepartmentName());
        DepartmentModel result = deparmentDao.findByUniqueIndex(param);
        if(result==null){
            deleteResult.setError("院系不存在!");
            throw new ServiceException("院系不存在!");
        }
        Boolean delete = deparmentDao.delete(Long.valueOf(result.getId()));
        deleteResult.setSuccess(delete);
        deleteResult.setCode(Code.SUCCESS.getStatus());
        deleteResult.setResult("删除成功");
        return deleteResult;
    }

    @Override
    public Response<List> listDepartment() {
        Response<List> list = new Response<>();
        List<DepartmentModel> departmentModels = deparmentDao.listAll();
        if(departmentModels==null) return list;
        List<DepartmentInfo> collect = departmentModels.stream().map(departmentModel -> {
            DepartmentInfo departmentInfo = new DepartmentInfo();
            departmentInfo.setDate(departmentModel.getCreateAt());
            departmentInfo.setName(departmentModel.getDepartmentName());
            departmentInfo.setId(departmentModel.getId());
            return departmentInfo;
        }).collect(Collectors.toList());
        list.setCode(Code.SUCCESS.getStatus());
        list.setResult(collect);
        return list;
    }

    @Override
    public Response<String> updateDepartment(DepartmentUpdateRequest request) {
        Response<String> response = new Response<>();
        Map<String, Object> map=Maps.newHashMap();
        map.put("departmentName",request.getOldDepartmentName());
        DepartmentModel departmentInfo = deparmentDao.findByUniqueIndex(map);
        if(departmentInfo==null){
            response.setCode(Code.ERROR.getStatus());
            response.setError("更新的用户不存在!");
            throw new ServiceException("更新的用户不存在!");
        }
        departmentInfo.setDepartmentName(request.getNewDepartmentName());
        departmentInfo.setUpdateAt(new Date());
        Boolean update = deparmentDao.update(departmentInfo);
        response.setCode(Code.SUCCESS.getStatus());
        response.setSuccess(update);
        response.setResult("更新成功");
        return response;
    }

    private void isExcludeDeparment(String departmentName, Response<Object> loginResponse) {
        Map<String, Object> parms= Maps.newHashMap();
        parms.put("departmentName",departmentName);
        DepartmentModel result = deparmentDao.findByUniqueIndex(parms);
        if(result!=null)
        {
            loginResponse.setError("院系名称已存在!");
            throw new ServiceException("院系名称已存在!");
        }
    }
}
