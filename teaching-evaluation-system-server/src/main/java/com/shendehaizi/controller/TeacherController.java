package com.shendehaizi.controller;

import com.shendehaizi.request.TeacherCourseAddRequest;
import com.shendehaizi.request.TeacherUpdateRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.response.UserInfo;
import com.shendehaizi.service.TeacherService;
import com.shendehaizi.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserInfoService userInfoService;
    @PostMapping(value = "teacher/update")
    public Response<String> updateTeacherInfo(@RequestBody TeacherUpdateRequest request){
        return teacherService.handleUpdateInfo(request);
    }

    @PostMapping(value = "teacher/course/add/{userId}")
    public Response<String> addTeacherCourse(@RequestBody TeacherCourseAddRequest request,@PathVariable("userId") String userId){
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        request.setUserId(userInfo.getUserId());
        return  teacherService.addTeacherCourse(request);
    }
}
