package com.shendehaizi.dao;

import com.shendehaizi.model.StudentModel;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao extends MyBatisDao<StudentModel> {
    public Long getCount(){
        return this.sqlSession.selectOne(sqlId("count"));
    }
}
