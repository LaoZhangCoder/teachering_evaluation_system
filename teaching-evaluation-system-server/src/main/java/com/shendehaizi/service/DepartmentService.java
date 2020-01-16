package com.shendehaizi.service;

import com.shendehaizi.request.DeparmentRequest;
import com.shendehaizi.response.Response;

public interface DepartmentService {
    Response<Object> addDepartment(DeparmentRequest departmentName);
}
