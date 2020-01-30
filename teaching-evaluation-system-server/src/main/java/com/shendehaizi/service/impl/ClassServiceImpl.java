package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.ClassDao;
import com.shendehaizi.dao.ClassMajorDepartmentRelationDao;
import com.shendehaizi.dao.DeparmentDao;
import com.shendehaizi.dao.MajorDao;
import com.shendehaizi.enums.Code;
import com.shendehaizi.model.ClassMajorDepartmentRelationModel;
import com.shendehaizi.model.ClassModel;
import com.shendehaizi.model.DepartmentModel;
import com.shendehaizi.model.MajorModel;
import com.shendehaizi.request.ClassAddeRquest;
import com.shendehaizi.request.ClassUpdateRequest;
import com.shendehaizi.response.ClassInfo;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassDao classDao;
    @Autowired
    private ClassMajorDepartmentRelationDao classMajorDepartmentRelationDao;
    @Autowired
    private MajorDao majorDao;
    @Autowired
    private DeparmentDao deparmentDao;
    @Override
    public Response<String> addClass(ClassAddeRquest request) {
        Response<String> response = new Response<>();
        //添加前先判断是否有相同名称的班级名
        Response<String> successAddClass = isSuccessAddClass(request, response);
        //添加班级和院系和专业的关系表
        Map<String, Object> map = Maps.newHashMap();
        map.put("className", request.getName());
        ClassModel classModel = classDao.findByUniqueIndex(map);
        ClassMajorDepartmentRelationModel classMajorDepartmentRelationModel = new ClassMajorDepartmentRelationModel();
        classMajorDepartmentRelationModel.setClassId(classModel.getId());
        classMajorDepartmentRelationModel.setCreateDate(new Date());
        classMajorDepartmentRelationModel.setDepartmentId(request.getDepartmentId());
        classMajorDepartmentRelationModel.setMajorId(request.getMajorId());
        classMajorDepartmentRelationModel.setUpdateDate(new Date());
        classMajorDepartmentRelationDao.create(classMajorDepartmentRelationModel);
        return  successAddClass;
    }

    @Override
    public Response<List<ClassInfo>> listClass() {
        Response<List<ClassInfo>> listResponse = new Response<>();
        List<ClassModel> classModels = classDao.listAll();
        List<ClassInfo> classInfos = new ArrayList<>();
        classModels.stream().forEach(classModel -> {
            ClassInfo classInfo = new ClassInfo();
            Map<String, Object> map=Maps.newHashMap();
            map.put("classId",classModel.getId());
            ClassMajorDepartmentRelationModel classMajorDepartmentRelationModel = classMajorDepartmentRelationDao.findByUniqueIndex(map);
            if(classMajorDepartmentRelationModel!=null){
                map.clear();
                map.put("id",classMajorDepartmentRelationModel.getDepartmentId());
                DepartmentModel departmentModel = deparmentDao.findByUniqueIndex(map);
                if(departmentModel!=null) {
                    map.clear();
                    map.put("id", classMajorDepartmentRelationModel.getMajorId());
                    MajorModel majorModel = majorDao.findByUniqueIndex(map);
                    classInfo.setClassId(classMajorDepartmentRelationModel.getClassId());
                    classInfo.setClassName(classModel.getClassName());
                    classInfo.setDate(classModel.getCreateDate());
                    classInfo.setDepartmentId(departmentModel.getId());
                    classInfo.setDepartmentName(departmentModel.getDepartmentName());
                    classInfo.setMajorId(majorModel.getId());
                    classInfo.setMajorName(majorModel.getMajorName());
                    classInfos.add(classInfo);
                }
            }
        });
        listResponse.setCode(Code.SUCCESS.getStatus());
        listResponse.setResult(classInfos);
        listResponse.setSuccess(true);
        return listResponse;
    }

    @Override
    public Response<String> deleteClassInfo(Long classId) {
        Response<String> response = new Response<>();
        Boolean delete = classDao.delete(classId);
        if(delete){
            //删除关系表
            classMajorDepartmentRelationDao.deleteByClassId(classId);
        }
        response.setCode(Code.SUCCESS.getStatus());
        response.setResult("删除成功");
        response.setSuccess(delete);
        return  response;
    }

    @Override
    public Response<String> updateClassInfo(ClassUpdateRequest request) {
        Response<String> response = new Response<>();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("className",request.getClassName());
        ClassModel classModel = classDao.findByUniqueIndex(map);
        if(classModel!=null&&!classModel.getId().equals(request.getClassId().intValue())){
            log.error("存在相同名称的班级名");
            throw new ServiceException("存在相同名称的班级名");
        }
        ClassModel t=new ClassModel();
        t.setUpdateDate(new Date());
        t.setId(request.getClassId().intValue());
        t.setClassName(request.getClassName());
        Boolean update = classDao.update(t);
        if(update){
            //更新关系表
            map.clear();
            map.put("classId",request.getClassId());
            map.put("departmentId",request.getOldDepartmentId());
            map.put("majorId",request.getOldMajorId());
            ClassMajorDepartmentRelationModel byUniqueIndex = classMajorDepartmentRelationDao.findByUniqueIndex(map);
            ClassMajorDepartmentRelationModel classRel=new ClassMajorDepartmentRelationModel();
            Boolean delete = classMajorDepartmentRelationDao.delete(byUniqueIndex.getId().longValue());
            if(delete){
                classRel.setUpdateDate(new Date());
                classRel.setMajorId(request.getMajorId().intValue());
                classRel.setDepartmentId(request.getDepartmentId().intValue());
                classRel.setClassId(request.getClassId().intValue());
                classRel.setCreateDate(byUniqueIndex.getCreateDate());
                classMajorDepartmentRelationDao.create(classRel);
              response.setResult("修改成功!");
              response.setCode(Code.SUCCESS.getStatus());
              return  response;
            }
        }
        response.setError("添加失败!");
        return  response;
    }

    private Response<String> isSuccessAddClass(ClassAddeRquest request, Response<String> response) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("className", request.getName());
        ClassModel classModel = classDao.findByUniqueIndex(map);
        if (classModel != null) {
            log.error("存在相同名称的班级名");
            throw new ServiceException("存在相同名称的班级名");
        }
        ClassModel addClass = new ClassModel();
        addClass.setClassName(request.getName());
        addClass.setCreateDate(new Date());
        addClass.setUpdateDate(new Date());
        //添加班级
        Boolean isSuccess = classDao.create(addClass);
        if (isSuccess) {
            response.setSuccess(true);
            response.setResult("添加成功");
            response.setCode(Code.SUCCESS.getStatus());
            return response;
        }
        log.error("添加失败！");
        throw new ServiceException("添加失败！");
    }
}
