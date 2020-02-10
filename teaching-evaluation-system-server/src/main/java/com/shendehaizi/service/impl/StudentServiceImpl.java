package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.dao.ClassDao;
import com.shendehaizi.dao.DeparmentDao;
import com.shendehaizi.dao.MajorDao;
import com.shendehaizi.dao.StudentDao;
import com.shendehaizi.model.ClassModel;
import com.shendehaizi.model.DepartmentModel;
import com.shendehaizi.model.MajorModel;
import com.shendehaizi.model.StudentModel;
import com.shendehaizi.response.Response;
import com.shendehaizi.response.StudentInfo;
import com.shendehaizi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private DeparmentDao deparmentDao;
    @Autowired
    private MajorDao majorDao;
    @Autowired
    private ClassDao classDao;
    @Override
    public Response<StudentInfo> getStudentInfoDetail(String userId) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userId",userId);
        Response<StudentInfo> response = new Response<>();
        StudentInfo studentInfo = new StudentInfo();
        StudentModel studentModel = studentDao.findByUniqueIndex(map);
        if(studentModel!=null){
            map.clear();
            map.put("id",studentModel.getDepartmentId());
            DepartmentModel departmentModel = deparmentDao.findByUniqueIndex(map);
            studentInfo.setDepartmentName(departmentModel.getDepartmentName());
            map.clear();
            map.put("id",studentModel.getMajorId());
            MajorModel major = majorDao.findByUniqueIndex(map);
            studentInfo.setMajorName(major.getMajorName());
            map.clear();
            map.put("id",studentModel.getClassId());
            ClassModel classModel = classDao.findByUniqueIndex(map);
            studentInfo.setClassName(classModel.getClassName());
        }
        response.setResult(studentInfo);
        return  response;
    }
}
