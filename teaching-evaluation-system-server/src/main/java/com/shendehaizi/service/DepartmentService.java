package com.shendehaizi.service;

import com.shendehaizi.request.DeparmentRequest;
import com.shendehaizi.request.DepartmentUpdateRequest;
import com.shendehaizi.response.Response;

import java.util.List;

public interface DepartmentService {
    Response<Object> addDepartment(DeparmentRequest departmentName);
    Response<Object> deleteDepartment(DeparmentRequest departmentName);
    Response<List>   listDepartment();
    Response<String> updateDepartment(DepartmentUpdateRequest request);
}
