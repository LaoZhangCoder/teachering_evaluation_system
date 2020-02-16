package com.shendehaizi.service;

import com.shendehaizi.request.TeacherCourseAddRequest;
import com.shendehaizi.request.TeacherUpdateRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.response.TeacherCourseInfo;
import com.shendehaizi.response.TeacherScoreRecord;

import java.util.List;

public interface TeacherService {
    Response<String> handleUpdateInfo(TeacherUpdateRequest request);

    Response<String> addTeacherCourse(TeacherCourseAddRequest request);

    Response<List<TeacherCourseInfo>> getTeacherCourseInfo(String userId);

    Response<String> deleteTeacherInfo(Long id);

    Response<List<TeacherCourseInfo>> getPagingTeacherCourserInfo(Integer currentPage, int i,String userId);

    Response<List<TeacherScoreRecord>> getPageTeacherScoreRecords(Integer currentPage, int i,Long teacherId);

}
