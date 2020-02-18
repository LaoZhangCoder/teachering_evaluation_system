package com.shendehaizi.dao;

import com.shendehaizi.model.ScoreCourseModel;
import com.shendehaizi.model.ScoreRecord;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScoreRecordDao extends MyBatisDao<ScoreRecord> {
    public List<ScoreCourseModel> getScoreCount(Long teacherId){
        return this.sqlSession.selectList(sqlId("listCountScore"),teacherId);
    }
    public Long getCount(){
        return this.sqlSession.selectOne(sqlId("count"));
    }
}
