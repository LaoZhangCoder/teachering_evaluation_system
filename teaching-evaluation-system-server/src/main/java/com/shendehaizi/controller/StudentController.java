package com.shendehaizi.controller;

import com.shendehaizi.request.ScoreTeacherInfo;
import com.shendehaizi.request.StudentUpdateRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.response.StudentInfo;
import com.shendehaizi.response.TeacherScoreInfo;
import com.shendehaizi.response.UserInfo;
import com.shendehaizi.service.StudentService;
import com.shendehaizi.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserInfoService userInfoService;


    @GetMapping(value = "student/detail")
    public Response getStudentInfoDetail(String userId) {
        return studentService.getStudentInfoDetail(userId);
    }

    @PostMapping(value = "student/update")
    public Response<String> updateStudentInfo(@RequestBody StudentUpdateRequest request) {
        return studentService.handleUpdateInfo(request);
    }

    @GetMapping(value = "student/teacher/score")
    public Response<List<TeacherScoreInfo>> getTeacherScoreInfos(ScoreTeacherInfo request) {
        UserInfo userInfo = userInfoService.getUserInfo(request.getToken());
        Response<StudentInfo> studentInfoDetail = studentService.getStudentInfoDetail(userInfo.getUserId());
        request.setClassId(studentInfoDetail.getResult().getClassId());
        System.out.println(request.toString());
        return studentService.getTeacherScoreInfos(request);
    }

}
