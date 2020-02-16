package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.*;
import com.shendehaizi.model.*;
import com.shendehaizi.request.TeacherCourseAddRequest;
import com.shendehaizi.request.TeacherUpdateRequest;
import com.shendehaizi.response.CourseInfo;
import com.shendehaizi.response.Response;
import com.shendehaizi.response.TeacherCourseInfo;
import com.shendehaizi.response.TeacherScoreRecord;
import com.shendehaizi.service.TeacherService;
import io.terminus.common.model.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private TeacherCourseRelationDao teacherCourseRelationDao;

    @Autowired
    private DeparmentDao deparmentDao;

    @Autowired
    private MajorDao majorDao;

    @Autowired
    private ClassDao classDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private ScoreRecordDao scoreRecordDao;

    @Override
    public Response<String> handleUpdateInfo(TeacherUpdateRequest request) {
        Response<String> response = new Response<>();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userId", request.getUserId());
        TeacherModel teacherModel = teacherDao.findByUniqueIndex(map);
        teacherModel.setUpdateDate(new Date());
        teacherModel.setTeacherName(request.getName());
        teacherModel.setPassword(request.getPassword());
        Boolean update = teacherDao.update(teacherModel);
        if (update) {
            response.setResult("更新成功!");
            return response;
        }
        log.error("学生信息更新失败!");
        throw new ServiceException("更新失败!");
    }

    @Override
    public Response<String> addTeacherCourse(TeacherCourseAddRequest request) {
        Response<String> response = new Response<>();
        TeacherCourseRelationModel teacherCourseRelationModel = new TeacherCourseRelationModel();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userId", request.getUserId());
        map.put("departmentId", request.getDepartmentId());
        map.put("majorId", request.getMajorId());
        map.put("classId", request.getClassId());
        map.put("courseId", request.getCourseId());
        TeacherCourseRelationModel relationModel = teacherCourseRelationDao.findByUniqueIndex(map);
        if (relationModel != null) {
            throw new ServiceException("该课程已添加，请勿重复添加!");
        }
        BeanUtils.copyProperties(request, teacherCourseRelationModel);
        teacherCourseRelationModel.setCreateDate(new Date());
        teacherCourseRelationModel.setUpdateDate(new Date());
        Boolean success = teacherCourseRelationDao.create(teacherCourseRelationModel);
        if (success) {
            response.setResult("添加成功!");
        } else {
            response.setError("添加失败!");
        }
        return response;
    }

    @Override
    public Response<List<TeacherCourseInfo>> getTeacherCourseInfo(String userId) {
        Response<List<TeacherCourseInfo>> listResponse = new Response<>();
        if (userId == null) {
            throw new ServiceException("发生错误,该用户不存在!");
        }
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        Paging<TeacherCourseRelationModel> paging = teacherCourseRelationDao.paging(0, 8,map);
        if (paging.getData().isEmpty()) {
            listResponse.setResult(null);
            return listResponse;
        }
        listResponse.setCount(paging.getTotal());
        List<TeacherCourseInfo> collect = paging.getData().stream().map(getTeacherCourseInfos(map)).collect(Collectors.toList());
        listResponse.setResult(collect);
        return listResponse;
    }

    @Override
    public Response<String> deleteTeacherInfo(Long id) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("id",id);
        TeacherCourseRelationModel teacherCourseRelationModel = teacherCourseRelationDao.findByUniqueIndex(map);
        if(teacherCourseRelationModel==null){
            log.error("删除的对象不存在！");
            throw new ServiceException("删除的对象不存在!");
        }
        teacherCourseRelationDao.delete(teacherCourseRelationModel.getId());
        Response<String> response = new Response<>();
        response.setResult("删除成功!");
        return response;
    }

    @Override
    public Response<List<TeacherCourseInfo>> getPagingTeacherCourserInfo(Integer currentPage, int pageSize,String userId) {
        int offset = (currentPage - 1) * pageSize;
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userId",userId);
        Paging<TeacherCourseRelationModel> paging = teacherCourseRelationDao.paging(offset, pageSize,map);
        Response<List<TeacherCourseInfo>> listResponse = new Response<>();
        listResponse.setCount(paging.getTotal());
        if (!paging.getData().isEmpty()) {
            List<TeacherCourseInfo> collect = paging.getData().stream().map(getTeacherCourseInfos(map)).collect(Collectors.toList());
            listResponse.setResult(collect);
        }
        return listResponse;
    }

    @Override
    public Response<List<TeacherScoreRecord>> getPageTeacherScoreRecords(Integer currentPage, int pageSize, Long teacherId) {
        int offset = (currentPage - 1) * pageSize;
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("teacherId",teacherId);
        Paging<ScoreRecord> paging1 = scoreRecordDao.paging(offset, pageSize,map);
        Response<List<TeacherScoreRecord>> listResponse = new Response<>();
        listResponse.setCount(paging1.getTotal());
        if (!paging1.getData().isEmpty()) {
            List<TeacherScoreRecord> collect = paging1.getData().stream().map(scoreRecord -> {
                TeacherScoreRecord teacherScoreRecord = new TeacherScoreRecord();
                teacherScoreRecord.setId(scoreRecord.getId());
                map.clear();
                map.put("id", scoreRecord.getCourseId());
                CourseModel courseModel = courseDao.findByUniqueIndex(map);
                if (courseModel != null) {
                    teacherScoreRecord.setCourseName(courseModel.getCourseName());
                }
                teacherScoreRecord.setDate(scoreRecord.getCreateDate());
                teacherScoreRecord.setMessage(scoreRecord.getMessage());
                teacherScoreRecord.setScore(scoreRecord.getScore());
                return teacherScoreRecord;
            }).collect(Collectors.toList());
            listResponse.setResult(collect);
        }
        return listResponse;
    }

    private Function<TeacherCourseRelationModel, TeacherCourseInfo> getTeacherCourseInfos(HashMap<String, Object> map) {
        return teacherCourseRelationModel -> {
            TeacherCourseInfo teacherCourseInfo = new TeacherCourseInfo();
            map.clear();
            map.put("id", teacherCourseRelationModel.getClassId());
            ClassModel classModel = classDao.findByUniqueIndex(map);
            teacherCourseInfo.setClassName(classModel.getClassName());
            map.clear();
            map.put("id", teacherCourseRelationModel.getMajorId());
            MajorModel majorModel = majorDao.findByUniqueIndex(map);
            teacherCourseInfo.setMajorName(majorModel.getMajorName());
            map.clear();
            map.put("id", teacherCourseRelationModel.getDepartmentId());
            DepartmentModel departmentModel = deparmentDao.findByUniqueIndex(map);
            teacherCourseInfo.setDepartmentName(departmentModel.getDepartmentName());
            map.clear();
            map.put("id", teacherCourseRelationModel.getCourseId());
            CourseModel courseModel = courseDao.findByUniqueIndex(map);
            teacherCourseInfo.setCourseName(courseModel.getCourseName());
            teacherCourseInfo.setTeacherId(teacherCourseRelationModel.getUserId());
            teacherCourseInfo.setId(teacherCourseRelationModel.getId());
            return teacherCourseInfo;
        };
    }
}
