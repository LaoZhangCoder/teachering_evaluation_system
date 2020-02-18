package com.shendehaizi.dao;

import com.shendehaizi.model.ClassModel;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

@Repository
public class ClassDao extends MyBatisDao<ClassModel> {
    public Long getCount(){
        return this.sqlSession.selectOne(sqlId("count"));
    }
}
