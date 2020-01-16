package com.shendehaizi.controller;

import com.shendehaizi.request.DeparmentRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @GetMapping(value = "department/add")
    public Response handleLogin( DeparmentRequest request) {
        Response<Object> response = departmentService.addDepartment(request);
        return response;
    }
}
