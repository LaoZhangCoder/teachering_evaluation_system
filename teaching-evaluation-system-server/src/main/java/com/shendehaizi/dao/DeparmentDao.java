package com.shendehaizi.dao;

import com.shendehaizi.model.DepartmentModel;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

@Repository
public class DeparmentDao extends MyBatisDao<DepartmentModel> {
    public Boolean addDeparment(DepartmentModel departmentModel){
        return this.create(departmentModel);
    }
}
