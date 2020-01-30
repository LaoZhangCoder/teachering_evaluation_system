package com.shendehaizi.dao;

import com.shendehaizi.model.ClassMajorDepartmentRelationModel;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

@Repository
public class ClassMajorDepartmentRelationDao extends MyBatisDao<ClassMajorDepartmentRelationModel> {
    public void deleteByClassId(Long classId) {
        this.sqlSession.delete(sqlId("deleteByClassId"),classId);
    }

}
