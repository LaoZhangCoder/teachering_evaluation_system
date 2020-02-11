package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.ClassDao;
import com.shendehaizi.dao.CourseDao;
import com.shendehaizi.dao.DeparmentDao;
import com.shendehaizi.dao.MajorDao;
import com.shendehaizi.model.*;
import com.shendehaizi.request.CourseAddRequest;
import com.shendehaizi.request.CourseUpdateRequest;
import com.shendehaizi.response.CourseInfo;
import com.shendehaizi.response.CourseInfo;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.CourseService;
import io.terminus.common.model.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private DeparmentDao deparmentDao;

    @Autowired
    private MajorDao majorDao;

    @Autowired
    private ClassDao classDao;
    @Override
    public Response<String> addCourse(CourseAddRequest request) {
        Response<String> response = new Response<>();
        CourseModel courseModel = new CourseModel();
        BeanUtils.copyProperties(request,courseModel);
        courseModel.setCreateDate(new Date());
        courseModel.setUpdateDate(new Date());
        try {
            Boolean res = courseDao.create(courseModel);
            if(res){
                response.setResult("添加成功!");
            }
        } catch (Exception e) {
            log.error("添加失败!该班级已存在该课程");
            response.setError("请勿重复添加相同课程");
        }
        return response;
    }

    @Override
    public Response<List<CourseInfo>> getCourseInfo() {
        Response<List<CourseInfo>> listResponse = new Response<>();
        Paging<CourseModel> paging = courseDao.paging(0, 8);
        List<CourseModel> courseModels = paging.getData();
        listResponse.setCount(paging.getTotal());
        if (courseModels != null) {
            List<CourseInfo> collect = courseModels.stream().map(courseModel -> getCourseInfo(courseModel)).collect(Collectors.toList());
            listResponse.setResult(collect);
        }
        return listResponse;
    }

    @Override
    public Response<String> updateCourseInfo(CourseUpdateRequest request, Long id) {
        Response<String> response = new Response<>();
        CourseModel courseModel=new CourseModel();
        courseModel.setId(id);
        BeanUtils.copyProperties(request,courseModel);
        courseModel.setUpdateDate(new Date());
        try {
            courseDao.update(courseModel);
            response.setResult("更新成功!");
        } catch (Exception e) {
            response.setError("更新失败！已存在该课程!");
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response<String> deleteCourseInfo(Long id) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("id",id);
        CourseModel CourseModel = courseDao.findByUniqueIndex(map);
        if(CourseModel==null){
            log.error("删除的对象不存在！");
            throw new ServiceException("删除的对象不存在!");
        }
        courseDao.delete(CourseModel.getId());
        Response<String> response = new Response<>();
        response.setResult("删除成功!");
        return response;
    }

    @Override
    public Response<List<CourseInfo>> getPagingCourserInfo(Integer currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        Paging<CourseModel> paging = courseDao.paging(offset, pageSize);
        Response<List<CourseInfo>> listResponse = new Response<>();
        listResponse.setCount(paging.getTotal());
        if (!paging.getData().isEmpty()) {
            List<CourseInfo> collect = paging.getData().stream().map(courseModel -> getCourseInfo(courseModel)).collect(Collectors.toList());
            listResponse.setResult(collect);
        }
        return listResponse;
    }

    private CourseInfo getCourseInfo(CourseModel courseModel) {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setId(courseModel.getId());
        courseInfo.setCourseName(courseModel.getCourseName());
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("id", courseModel.getDepartmentId());
        DepartmentModel departmentModel = deparmentDao.findByUniqueIndex(map);
        if (departmentModel != null) {
            courseInfo.setDepartmentName(departmentModel.getDepartmentName());
        }
        map.clear();
        map.put("id", courseModel.getMajorId());
        MajorModel m = majorDao.findByUniqueIndex(map);
        if (m != null) {
            courseInfo.setMajorName(m.getMajorName());
        }
        map.clear();
        map.put("id", courseModel.getClassId());
        ClassModel classModel = classDao.findByUniqueIndex(map);
        if (classModel != null) {
            courseInfo.setClassName(classModel.getClassName());
        }
        return courseInfo;
    }
}
