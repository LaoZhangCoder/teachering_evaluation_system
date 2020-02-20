package com.shendehaizi.controller;

import com.shendehaizi.request.TeacherCourseAddRequest;
import com.shendehaizi.request.TeacherUpdateRequest;
import com.shendehaizi.response.*;
import com.shendehaizi.service.TeacherService;
import com.shendehaizi.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping(value = "teacher/course/list/{userId}")
    public Response<List<TeacherCourseInfo>> getTeacherCourseInfo(@PathVariable("userId") String userId) {
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        return teacherService.getTeacherCourseInfo(userInfo.getUserId());
    }
    @GetMapping(value = "teacher/course/delete/{id}")
    public Response<String> deleteTeacherInfo(@PathVariable("id") Long id) {
        return teacherService.deleteTeacherInfo(id);
    }
    @GetMapping(value = "teacher/course/page")
    public Response<List<TeacherCourseInfo>> getPagingTeacherCourserInfo(Integer currentPage,String userId) {
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        return teacherService.getPagingTeacherCourserInfo(currentPage, 8,userInfo.getUserId());
    }

    @GetMapping(value = "teacher/score/record")
    public Response<List<TeacherScoreRecord>> getPageTeacherScoreRecords(Integer currentPage,String userId){
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        return teacherService.getPageTeacherScoreRecords(currentPage,8,userInfo.getId());
    }
    @GetMapping(value = "teacher/score/countList")
    public Response<List<ScoreCourseInfo>> getScoreCountList(String token){
        UserInfo userInfo = userInfoService.getUserInfo(token);
        return teacherService.getScoreCountList(userInfo.getId());
    }
    @GetMapping(value = "teacher/score/detail")
    public Response<List<ScoreCourseInfo>> getScoreCountListDetail(Long userId){
        return teacherService.getScoreCountList(userId);
    }
}
