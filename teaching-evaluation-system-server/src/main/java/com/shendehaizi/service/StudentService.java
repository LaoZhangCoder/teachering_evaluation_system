package com.shendehaizi.service;

import com.shendehaizi.response.Response;
import com.shendehaizi.response.StudentInfo;

public interface StudentService {
    Response<StudentInfo> getStudentInfoDetail(String userId);
}
