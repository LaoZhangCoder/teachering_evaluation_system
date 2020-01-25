package com.shendehaizi.controller;

import com.shendehaizi.request.DeparmentRequest;
import com.shendehaizi.request.DepartmentUpdateRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @GetMapping(value = "department/add")
    public Response addDepartment( DeparmentRequest request) {
        Response<Object> response = departmentService.addDepartment(request);
        return response;
    }
    @GetMapping(value = "department/delete")
    public Response deleteDepartment(DeparmentRequest request){
         return  departmentService.deleteDepartment(request);
    }

    @GetMapping(value = "department/list")
    public Response<List> listDepartments() {
        return departmentService.listDepartment();
    }

    @GetMapping(value = "department/update")
    public Response<String> updateDepartment(DepartmentUpdateRequest request){
        Response<String> response=departmentService.updateDepartment(request);
        return  response;
    }
}
