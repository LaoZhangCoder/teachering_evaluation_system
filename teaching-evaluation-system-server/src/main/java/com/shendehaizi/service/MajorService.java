package com.shendehaizi.service;

import com.shendehaizi.request.MajorAddRequest;
import com.shendehaizi.request.MajorUpdateRequest;
import com.shendehaizi.response.MajorInfo;
import com.shendehaizi.response.Response;

import java.util.List;

public interface MajorService {
     Response<String> addMajor(MajorAddRequest request);

     Response<List<MajorInfo>> listMajor();

    Response<String> updateMajorInfo(MajorUpdateRequest request);

    Response<String> deleteMajorInfo(Long majorId);
}
