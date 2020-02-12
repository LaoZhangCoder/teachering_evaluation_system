package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.TeacherCourseRelationDao;
import com.shendehaizi.dao.TeacherDao;
import com.shendehaizi.model.TeacherCourseRelationModel;
import com.shendehaizi.model.TeacherModel;
import com.shendehaizi.request.TeacherCourseAddRequest;
import com.shendehaizi.request.TeacherUpdateRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private TeacherCourseRelationDao teacherCourseRelationDao;

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
}
