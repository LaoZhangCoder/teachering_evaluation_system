package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.DeparmentDao;
import com.shendehaizi.model.DepartmentModel;
import com.shendehaizi.request.DeparmentRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

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
        return loginResponse;
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
