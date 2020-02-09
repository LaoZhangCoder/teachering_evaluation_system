package com.shendehaizi.service;

import com.shendehaizi.request.ClassAddeRquest;
import com.shendehaizi.request.ClassUpdateRequest;
import com.shendehaizi.response.ClassInfo;
import com.shendehaizi.response.ClassesInfo;
import com.shendehaizi.response.Response;

import java.util.List;

public interface ClassService {
    Response<String> addClass(ClassAddeRquest request);

    Response<List<ClassInfo>> listClass();

    Response<String> deleteClassInfo(Long majorId);

    Response<String> updateClassInfo(ClassUpdateRequest request);

    Response<List<ClassesInfo>> listClassByMajorId(Long majorId);
}
