package com.shendehaizi.service;

import com.shendehaizi.request.StudentUpdateRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.response.StudentInfo;

public interface StudentService {
    Response<StudentInfo> getStudentInfoDetail(String userId);

    Response<String> handleUpdateInfo(StudentUpdateRequest request);
}
