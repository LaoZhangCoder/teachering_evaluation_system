package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.*;
import com.shendehaizi.model.*;
import com.shendehaizi.request.ScoreTeacherInfo;
import com.shendehaizi.request.StudentUpdateRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.response.StudentInfo;
import com.shendehaizi.response.TeacherScoreInfo;
import com.shendehaizi.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private DeparmentDao deparmentDao;
    @Autowired
    private MajorDao majorDao;
    @Autowired
    private ClassDao classDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private TeacherCourseRelationDao teacherCourseRelationDao;
    @Autowired
    private CourseDao courseDao;
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
            studentInfo.setClassId(studentModel.getClassId());
        }
        response.setResult(studentInfo);
        return  response;
    }

    @Override
    public Response<String> handleUpdateInfo(StudentUpdateRequest request) {
        Response<String> response = new Response<>();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userId",request.getUserId());
        StudentModel studentModel = studentDao.findByUniqueIndex(map);
        studentModel.setUpdateDate(new Date());
        studentModel.setStudentName(request.getName());
        studentModel.setPassword(request.getPassword());
        studentModel.setDepartmentId(request.getDepartmentId());
        studentModel.setMajorId(request.getMajorId());
        studentModel.setClassId(request.getClassId());
        Boolean update = studentDao.update(studentModel);
        if(update){
            response.setResult("更新成功!");
            return  response;
        }
        log.error("学生信息更新失败!");
        throw new ServiceException("更新失败!");
    }

    @Override
    public Response<List<TeacherScoreInfo>> getTeacherScoreInfos(ScoreTeacherInfo request) {
        Response<List<TeacherScoreInfo>> listResponse = new Response<>();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("classId",request.getClassId());
        List<TeacherCourseRelationModel> list = teacherCourseRelationDao.list(map);
        if(list.isEmpty()){
            listResponse.setResult(null);
            return listResponse;
        }
        List<TeacherScoreInfo> scoreInfoList = list.stream().map(teacherCourseRelationModel -> {
            TeacherScoreInfo teacherScoreInfo = new TeacherScoreInfo();
            String userId = teacherCourseRelationModel.getUserId();
            map.clear();
            map.put("userId", userId);
            TeacherModel t = teacherDao.findByUniqueIndex(map);
            teacherScoreInfo.setTeacherId(t.getId());
            teacherScoreInfo.setTeacherName(t.getTeacherName());
            map.clear();
            map.put("id", teacherCourseRelationModel.getCourseId());
            CourseModel courseModel = courseDao.findByUniqueIndex(map);
            teacherScoreInfo.setCourseName(courseModel.getCourseName());
            return teacherScoreInfo;
        }).collect(Collectors.toList());
        listResponse.setResult(scoreInfoList);
        return listResponse;
    }
}
