package com.shendehaizi.service;

import com.shendehaizi.request.CourseAddRequest;
import com.shendehaizi.request.CourseUpdateRequest;
import com.shendehaizi.response.CourseInfo;
import com.shendehaizi.response.Response;

import java.util.List;

public interface CourseService {
    Response<String> addCourse(CourseAddRequest request);

    Response<List<CourseInfo>> getCourseInfo();

    Response<String> updateCourseInfo(CourseUpdateRequest request, Long id);

    Response<String> deleteCourseInfo(Long id);

    Response<List<CourseInfo>> getPagingCourserInfo(Integer currentPage, int i);

    Response<List<CourseInfo>> listCourseByClassId(Long classId);
}
