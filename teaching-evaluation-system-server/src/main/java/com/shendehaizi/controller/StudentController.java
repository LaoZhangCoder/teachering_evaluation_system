package com.shendehaizi.controller;

import com.shendehaizi.request.StudentUpdateRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/")
public class StudentController {
@Autowired
private StudentService studentService;
    @GetMapping(value = "student/detail")
    public Response getStudentInfoDetail(String userId) {
      return  studentService.getStudentInfoDetail(userId);
    }

    @PostMapping(value = "student/update")
    public Response<String> updateStudentInfo(@RequestBody StudentUpdateRequest request){
        return studentService.handleUpdateInfo(request);
    }
}
