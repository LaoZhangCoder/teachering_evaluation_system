package com.shendehaizi.service.impl;

import com.google.common.collect.Maps;
import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.dao.DeparmentDao;
import com.shendehaizi.dao.MajorDao;
import com.shendehaizi.dao.MajorDepartmentRelationDao;
import com.shendehaizi.enums.Code;
import com.shendehaizi.model.DepartmentModel;
import com.shendehaizi.model.MajorDepartmentRelationModel;
import com.shendehaizi.model.MajorModel;
import com.shendehaizi.request.MajorAddRequest;
import com.shendehaizi.request.MajorUpdateRequest;
import com.shendehaizi.response.MajorInfo;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.MajorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MajorServiceImpl implements MajorService {
    @Autowired
    private MajorDao majorDao;
    @Autowired
    private MajorDepartmentRelationDao majorDepartmentRelationDao;

    @Autowired
    private DeparmentDao deparmentDao;

    @Override
    public Response<String> addMajor(MajorAddRequest request) {
        //是否有相同名称的专业名称
        Response<String> response = new Response<>();
        Map<String, Object> parms = Maps.newHashMap();
        parms.put("majorName", request.getName());
        MajorModel isExclude = majorDao.findByUniqueIndex(parms);
        if (isExclude != null) {
            log.error("该专业名称已存在请勿重复创建！");
            throw new ServiceException("该专业名称已存在请勿重复创建！");
        }
        //创建专业名称
        MajorModel majorModel = new MajorModel();
        majorModel.setCreateDate(new Date());
        majorModel.setMajorName(request.getName());
        majorModel.setUpdateDate(new Date());
        Boolean isSuccess = majorDao.create(majorModel);
        if (isSuccess) {
            response.setCode(Code.SUCCESS.getStatus());
            response.setResult("创建成功!");
        } else {
            log.error("该专业名称已存在请勿重复创建！");
            throw new ServiceException("该专业名称已存在请勿重复创建！");
        }
        //插入院系和专业的关系表
        //获取专业id
        MajorModel majorId = majorDao.findByUniqueIndex(parms);
        MajorDepartmentRelationModel majorDepartmentRelationModel = new MajorDepartmentRelationModel();
        majorDepartmentRelationModel.setCreateDate(new Date());
        majorDepartmentRelationModel.setUpdateDate(new Date());
        majorDepartmentRelationModel.setMajorId(majorId.getId());
        majorDepartmentRelationModel.setDepartmentId(request.getDepartmentId());
        Boolean res = majorDepartmentRelationDao.create(majorDepartmentRelationModel);
        if (res != true) {
            log.error("插入专业和院系记录失败！专业id,院系id", majorId.getId(), request.getDepartmentId());
            throw new ServiceException("插入专业和院系记录失败！");
        }
        return response;
    }

    @Override
    public Response<List<MajorInfo>> listMajor() {
        Response<List<MajorInfo>> listResponse = new Response<>();
        //院系表
        List<DepartmentModel> departmentModels = deparmentDao.listAll();
        if (departmentModels.isEmpty()) {
            return listResponse;
        }
        //专业记录表
        List<MajorModel> majorModels = majorDao.listAll();

        if (majorModels.isEmpty()) {
            return listResponse;
        }
        //专业记录院系关联表
        List<MajorDepartmentRelationModel> majorDepartmentRelationModels = majorDepartmentRelationDao.listAll();

        List<MajorInfo> majorInfoList = majorModels.stream().map(majorModel -> {
            MajorInfo majorInfo = new MajorInfo();
            majorDepartmentRelationModels.forEach(majorDepartmentRelationModel -> {
                if (majorDepartmentRelationModel.getMajorId().equals(majorModel.getId())) {
                    majorInfo.setDate(majorModel.getCreateDate());
                    majorInfo.setMajorId(majorModel.getId());
                    majorInfo.setMajorName(majorModel.getMajorName());
                    majorInfo.setDepartmentId(majorDepartmentRelationModel.getDepartmentId());
                }
            });
            return majorInfo;
        }).collect(Collectors.toList());
        //拼接部门名称
        majorInfoList.forEach(majorInfo -> {
            departmentModels.forEach(departmentModel -> {
                if (departmentModel.getId().equals(majorInfo.getDepartmentId())) {
                    majorInfo.setDepartmentName(departmentModel.getDepartmentName());
                }
            });
        });
        listResponse.setCode(Code.SUCCESS.getStatus());
        listResponse.setResult(majorInfoList);
        return listResponse;
    }

    @Override
    public Response<String> updateMajorInfo(MajorUpdateRequest request) {
        Response<String> response = new Response<>();
        Map<String, Object> map = Maps.newHashMap();
        map.put("majorName", request.getName());
        MajorModel majorModel = majorDao.findByUniqueIndex(map);
        if (isExcludeMajorName(request, majorModel)) {
            log.error("该专业名称已存在!");
            throw new ServiceException("该专业名称已存在!");
        }
        //更新该专业相关信息
        MajorModel major = new MajorModel();
        major.setMajorName(request.getName());
        major.setUpdateDate(new Date());
        major.setId(request.getMajorId());
        Boolean update = majorDao.update(major);
        if (update) {
            //更新和院系的关系表
            Map<String, Object> parm = Maps.newHashMap();
            parm.put("departmentId", request.getOldDepartmentId());
            parm.put("majorId", request.getMajorId());
            MajorDepartmentRelationModel majorDepartmentRelation = majorDepartmentRelationDao.findByUniqueIndex(parm);
            if (majorDepartmentRelation == null) {
                log.error("发生并发问题");
                throw new ServiceException("发生并发问题");
            }
            //删除旧的关系
            majorDepartmentRelationDao.delete(majorDepartmentRelation.getId().longValue());
            MajorDepartmentRelationModel t = new MajorDepartmentRelationModel();
            t.setDepartmentId(request.getDepartmentId());
            t.setMajorId(request.getMajorId());
            t.setUpdateDate(new Date());
            t.setCreateDate(new Date());
            //建立新的关系
            majorDepartmentRelationDao.create(t);
            response.setCode(200);
            response.setSuccess(true);
            response.setResult("更新成功!");
            return response;
        } else {
            return response;
        }
    }

    @Override
    public Response<String> deleteMajorInfo(Long majorId) {
        Response<String> response = new Response<>();
        Boolean delete = majorDao.delete(majorId);
        if(delete){
            //删除关系表
            majorDepartmentRelationDao.deleteByMajorId(majorId);
        }
        response.setCode(Code.SUCCESS.getStatus());
        response.setResult("删除成功");
        response.setSuccess(delete);
        return  response;
    }

    private boolean isExcludeMajorName(MajorUpdateRequest request, MajorModel majorModel) {
        return majorModel != null && !majorModel.getId().equals(request.getMajorId());
    }
}
