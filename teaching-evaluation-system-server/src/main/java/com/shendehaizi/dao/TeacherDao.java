package com.shendehaizi.dao;

import com.shendehaizi.model.TeacherModel;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDao extends MyBatisDao<TeacherModel> {
    public Long getCount(){
        return this.sqlSession.selectOne(sqlId("count"));
    }

}
