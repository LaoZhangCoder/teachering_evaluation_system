package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.*;
import com.shendehaizi.model.*;
import com.shendehaizi.model.AdminModel;
import com.shendehaizi.request.AdminAddRequest;
import com.shendehaizi.request.AdminUpdateRequest;
import com.shendehaizi.request.TeacherScoreRequest;
import com.shendehaizi.request.UserInfoRequest;
import com.shendehaizi.response.*;
import com.shendehaizi.response.AdminInfo;
import com.shendehaizi.service.AdminActivationService;
import io.terminus.common.model.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminActivationServiceImpl implements AdminActivationService {
    @Autowired
    private AdminDao adminDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private ClassDao classDao;

    @Autowired
    private ScoreRecordDao scoreRecordDao;

    @Autowired
    private TeacherCourseRelationDao teacherCourseRelationDao;

    @Override
    public Response<List<AdminInfo>> getAdminInfo() {
        Response<List<AdminInfo>> listResponse = new Response<>();
        Paging<AdminModel> paging = adminDao.paging(0, 8);
        List<AdminModel> AdminModels = paging.getData();
        listResponse.setCount(paging.getTotal());
        if (AdminModels != null) {
            List<AdminInfo> collect = AdminModels.stream().map(AdminModel -> {
                AdminInfo AdminInfo = new AdminInfo();
                AdminInfo.setAdminId(AdminModel.getAdminId());
                AdminInfo.setAdminName(AdminModel.getAdminName());
                return AdminInfo;
            }).collect(Collectors.toList());
            listResponse.setResult(collect);
        }
        return listResponse;
    }

    @Override
    public Response<String> addAdmin(AdminAddRequest request) {
        Response<String> response = new Response<>();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userName", request.getAdminName());
        AdminModel AdminModel = adminDao.findByUniqueIndex(map);
        if (AdminModel != null) {
            log.error("已存在该管理员名称！");
            throw new ServiceException("已存在该管理员名称,请重新命名!");
        }
        AdminModel = new AdminModel();
        AdminModel.setCreatedAt(new Date());
        AdminModel.setRoleId(3);
        AdminModel.setAdminId(UUID.randomUUID().toString());
        AdminModel.setUpdatedAt(new Date());
        AdminModel.setAdminName(request.getAdminName());
        AdminModel.setAdminPassword(request.getAdminPassword());
        adminDao.create(AdminModel);
        response.setResult("添加成功！");
        response.setCode(200);
        return response;
    }

    @Override
    public Response<List<AdminInfo>> getPagingAdminInfo(Integer currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        Paging<AdminModel> paging = adminDao.paging(offset, pageSize);
        Response<List<AdminInfo>> listResponse = new Response<>();
        listResponse.setCount(paging.getTotal());
        if (!paging.getData().isEmpty()) {
            List<AdminInfo> collect = paging.getData().stream().map(AdminModel -> {
                AdminInfo AdminInfo = new AdminInfo();
                AdminInfo.setAdminId(AdminModel.getAdminId());
                AdminInfo.setAdminName(AdminModel.getAdminName());
                return AdminInfo;
            }).collect(Collectors.toList());
            listResponse.setResult(collect);
        }
        return listResponse;
    }

    @Override
    public Response<String> updateAdminInfo(AdminUpdateRequest request, String id) {
        Response<String> response = new Response<>();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userName", request.getAdminName());
        AdminModel AdminModel = adminDao.findByUniqueIndex(map);
        map.put("userName", id);
        AdminModel oldUser = adminDao.findByUniqueIndex(map);
        if (oldUser == null) {
            log.error("发生错误，修改的数据不存在!");
            throw new ServiceException("发生错误，修改的数据不存在!");
        }
        if (AdminModel != null && !oldUser.getId().equals(AdminModel.getId())) {
            log.error("修改的用户id已存在请检测是否重复!");
            throw new ServiceException("修改的用户id已存在请检测是否重复!");
        }
        oldUser.setAdminName(request.getAdminName());
        oldUser.setUpdatedAt(new Date());
        oldUser.setAdminPassword(request.getAdminPassword());
        adminDao.update(oldUser);
        response.setResult("修改成功");
        return response;
    }

    @Override
    public Response<String> deleteAdminInfo(String id) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("adminId", id);
        AdminModel AdminModel = adminDao.findByUniqueIndex(map);
        if (AdminModel == null) {
            log.error("删除的对象不存在！");
            throw new ServiceException("删除的对象不存在!");
        }
        adminDao.delete(AdminModel.getId());
        Response<String> response = new Response<>();
        response.setResult("删除成功!");
        return response;
    }

    @Override
    public Response<List<UserInfoDetail>> getUserInfos(UserInfoRequest request) {
        Response<List<UserInfoDetail>> listResponse = new Response<>();
        List<StudentModel> studentModels = studentDao.listAll();
        List<TeacherModel> teacherModels = teacherDao.listAll();
        List<UserInfoDetail> studentList = studentModels.stream().map(studentModel -> {
            UserInfoDetail userInfoDetail = new UserInfoDetail();
            userInfoDetail.setDate(studentModel.getCreateDate());
            userInfoDetail.setId(studentModel.getId());
            userInfoDetail.setRoleName("学生");
            userInfoDetail.setUserName(studentModel.getStudentName());
            userInfoDetail.setUserId(studentModel.getUserId());
            return userInfoDetail;
        }).collect(Collectors.toList());
        List<UserInfoDetail> teacherList = teacherModels.stream().map(teacherModel -> {
            UserInfoDetail userInfoDetail = new UserInfoDetail();
            userInfoDetail.setDate(teacherModel.getCreateDate());
            userInfoDetail.setId(teacherModel.getId());
            userInfoDetail.setRoleName("教师");
            userInfoDetail.setUserName(teacherModel.getTeacherName());
            userInfoDetail.setUserId(teacherModel.getUserId());
            return userInfoDetail;
        }).collect(Collectors.toList());
        studentList.addAll(teacherList);
        List<UserInfoDetail> collect = studentList;
        collect = studentList.stream().filter(
                filter(request)
        ).skip((request.getPage() - 1) * request.getLimit()).limit(request.getLimit()).collect(Collectors.toList());
        if (StringUtils.isEmpty(request.getUserName())) {
            listResponse.setCount(Long.valueOf(studentList.size()));
        } else {
            listResponse.setCount(Long.valueOf(collect.size()));
        }
        listResponse.setResult(collect);
        return listResponse;
    }

    @Override
    public Response<CountInfo> getCountInfos() {
        Response<CountInfo> countInfoResponse = new Response<>();
        Long studentCount = studentDao.getCount();
        Long classCount = classDao.getCount();
        Long teacherCount = teacherDao.getCount();
        Long courseCount = courseDao.getCount();
        CountInfo countInfo = new CountInfo();
        countInfo.setStudentCount(studentCount);
        countInfo.setClassCount(classCount);
        countInfo.setTeacherCount(teacherCount);
        countInfo.setCourseCount(courseCount);
        countInfoResponse.setResult(countInfo);
        return countInfoResponse;
    }

    @Override
    public Response<ProcessInfo> getProcessInfo() {
        Response<ProcessInfo> processInfoResponse = new Response<>();
        //应该评教的总条数
        double count=0l;
        List<StudentModel> studentModels = studentDao.listAll();
        HashMap<String, Object> map = Maps.newHashMap();
        for (StudentModel studentModel : studentModels) {
            map.put("classId", studentModel.getClassId());
            List<TeacherCourseRelationModel> list = teacherCourseRelationDao.list(map);
            count = count + list.size();
        }
        //已评教的记录数
        double count1 = scoreRecordDao.getCount();

        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setComplete(Integer.valueOf(String.valueOf(count1/count*100).substring(0,2)));
        processInfo.setUnComplete(Integer.valueOf(String.valueOf((count-count1)/count*100).substring(0,2)));
        processInfoResponse.setResult(processInfo);
        return  processInfoResponse;
    }

    @Override
    public Response<List<TeacherScore>> getTeacherScoreList(TeacherScoreRequest request) {
        Response<List<TeacherScore>> listResponse = new Response<>();
        HashMap<String, Object> map = Maps.newHashMap();
        List<ScoreInfo> scoreInfoList = scoreRecordDao.getScoreInfoList();
        if(scoreInfoList.isEmpty()) return  listResponse;
        List<TeacherScore> teacherScoreList = scoreInfoList.stream().map(scoreInfo -> {
            TeacherScore teacherScore = new TeacherScore();
            teacherScore.setCount(scoreInfo.getCountScore());
            teacherScore.setId(scoreInfo.getTeacherId());
            map.put("id", scoreInfo.getTeacherId());
            TeacherModel t = teacherDao.findByUniqueIndex(map);
            teacherScore.setTeacherName(t.getTeacherName());
            map.clear();
            map.put("userId", t.getUserId());
            List<TeacherCourseRelationModel> list = teacherCourseRelationDao.list(map);
            if (!list.isEmpty()) {
                list.stream().forEach(teacherCourseRelationModel -> {
                    map.clear();
                    map.put("id", teacherCourseRelationModel.getCourseId());
                    CourseModel courseModel = courseDao.findByUniqueIndex(map);
                    if(teacherScore.getCourseNames()!=null) {
                        teacherScore.setCourseNames(teacherScore.getCourseNames() + courseModel.getCourseName()+','+'\n');
                    }else{
                        teacherScore.setCourseNames(courseModel.getCourseName());
                    }
                    map.put("id", teacherCourseRelationModel.getClassId());
                    ClassModel classModel = classDao.findByUniqueIndex(map);
                    if(teacherScore.getClassNames()!=null) {
                        teacherScore.setClassNames(teacherScore.getClassNames() + classModel.getClassName()+','+'\n');
                    }else{
                        teacherScore.setClassNames(classModel.getClassName());
                    }
                });
            }
            return teacherScore;

        }).collect(Collectors.toList());
        teacherScoreList=teacherScoreList.stream().filter(teacherScore -> {
            if(StringUtils.isEmpty(request.getTeacherName())){
                return  true;
            }else{
                if(request.getTeacherName().equals(teacherScore.getTeacherName())){
                    return  true;
                }else{
                    return  false;
                }
            }
        }).collect(Collectors.toList());
        listResponse.setCount(Long.valueOf(teacherScoreList.size()));
        teacherScoreList=teacherScoreList.stream().skip((request.getPage() - 1) * request.getLimit()).limit(request.getLimit()).collect(Collectors.toList());
        listResponse.setResult(teacherScoreList);
        return listResponse;
    }

    private Predicate<UserInfoDetail> filter(UserInfoRequest request) {
        return (UserInfoDetail userInfoDetail) -> {
            if (StringUtils.isEmpty(request.getUserName())) return true;
            return userInfoDetail.getUserName().equals(request.getUserName());
        };
    }
}
