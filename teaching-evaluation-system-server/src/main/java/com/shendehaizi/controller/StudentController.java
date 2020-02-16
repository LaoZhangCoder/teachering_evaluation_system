package com.shendehaizi.controller;

import com.google.common.collect.Maps;
import com.shendehaizi.dao.CourseDao;
import com.shendehaizi.dao.ScoreRecordDao;
import com.shendehaizi.model.CourseModel;
import com.shendehaizi.model.ScoreRecord;
import com.shendehaizi.request.ScoreRequest;
import com.shendehaizi.request.ScoreTeacherInfo;
import com.shendehaizi.request.StudentScoreRequest;
import com.shendehaizi.request.StudentUpdateRequest;
import com.shendehaizi.response.*;
import com.shendehaizi.service.StudentService;
import com.shendehaizi.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private ScoreRecordDao scoreRecordDao;


    @GetMapping(value = "student/detail")
    public Response getStudentInfoDetail(String userId) {
        return studentService.getStudentInfoDetail(userId);
    }

    @PostMapping(value = "student/update")
    public Response<String> updateStudentInfo(@RequestBody StudentUpdateRequest request) {
        return studentService.handleUpdateInfo(request);
    }

    @GetMapping(value = "student/teacher/score")
    public Response<List<TeacherScoreInfo>> getTeacherScoreInfos(ScoreTeacherInfo request) {
        UserInfo userInfo = userInfoService.getUserInfo(request.getToken());
        Response<StudentInfo> studentInfoDetail = studentService.getStudentInfoDetail(userInfo.getUserId());
        request.setClassId(studentInfoDetail.getResult().getClassId());
        Response<List<TeacherScoreInfo>> teacherScoreInfos = studentService.getTeacherScoreInfos(request);
        HashMap<String, Object> map = Maps.newHashMap();
        teacherScoreInfos.getResult().stream().forEach(teacherScoreInfo -> {
            map.put("courseName",teacherScoreInfo.getCourseName());
            CourseModel courseModel = courseDao.findByUniqueIndex(map);
            map.clear();
            map.put("courseId", courseModel.getId());
            map.put("teacherId",teacherScoreInfo.getTeacherId());
            map.put("studentId", userInfo.getUserId());
            ScoreRecord scoreRecord = scoreRecordDao.findByUniqueIndex(map);
            if(scoreRecord==null){
                teacherScoreInfo.setStatus("未评教");
            }else{
                teacherScoreInfo.setStatus("已评教");
            }
            map.clear();
        });
        return teacherScoreInfos;
    }

    @PostMapping(value = "/student/start/score")
    public Response<String> handleScore(@RequestBody  StudentScoreRequest request){
        ScoreRequest scoreRequest = new ScoreRequest();
        UserInfo userInfo = userInfoService.getUserInfo(request.getStudentId());
       scoreRequest.setCountScore(request.getCountScore());
       scoreRequest.setMessage(request.getMessage());
       scoreRequest.setTeacherId(Long.valueOf(request.getTeacherId()));
       scoreRequest.setStudentId(userInfo.getUserId());
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("courseName",request.getCourseId());
        CourseModel courseModel = courseDao.findByUniqueIndex(map);
        scoreRequest.setCourseId(courseModel.getId());
        return studentService.handleScore(scoreRequest);
    }
    @GetMapping(value = "/student/score/list")
    public Response<List<StudentScoreRecord>> getScoreRecords(String userId){
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        return  studentService.getScoreRecords(userInfo.getUserId());
    }

}
