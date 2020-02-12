package com.shendehaizi.controller;

import com.shendehaizi.request.CourseAddRequest;
import com.shendehaizi.request.CourseUpdateRequest;
import com.shendehaizi.request.UserActivationUpdateRequest;
import com.shendehaizi.response.ActivationUserInfo;
import com.shendehaizi.response.ClassesInfo;
import com.shendehaizi.response.CourseInfo;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping(value = "/user/course/add")
    public Response<String> addCourse(@RequestBody CourseAddRequest request) {
        return courseService.addCourse(request);
    }

    @GetMapping(value = "user/course/list")
    public Response<List<CourseInfo>> getCourseInfo() {
        return courseService.getCourseInfo();
    }

    @PostMapping(value = "user/course/update/{id}")
    public Response<String> updateCourseInfo(@RequestBody CourseUpdateRequest request, @PathVariable("id") Long id) {
        return courseService.updateCourseInfo(request, id);
    }

    @GetMapping(value = "user/course/delete/{id}")
    public Response<String> deleteCourseInfo(@PathVariable("id") Long id) {
        return courseService.deleteCourseInfo(id);
    }

    @GetMapping(value = "user/course/page")
    public Response<List<CourseInfo>> getPagingCourserInfo(Integer currentPage) {
        return courseService.getPagingCourserInfo(currentPage, 8);
    }

    @GetMapping(value = "/course/list/condition")
    public Response<List<CourseInfo>> listCourseInfoByCondition(Long classId) {
        return courseService.listCourseByClassId(classId);
    }
}
