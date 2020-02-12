package com.shendehaizi.service;

import com.shendehaizi.request.TeacherCourseAddRequest;
import com.shendehaizi.request.TeacherUpdateRequest;
import com.shendehaizi.response.Response;

public interface TeacherService {
    Response<String> handleUpdateInfo(TeacherUpdateRequest request);

    Response<String> addTeacherCourse(TeacherCourseAddRequest request);
}
