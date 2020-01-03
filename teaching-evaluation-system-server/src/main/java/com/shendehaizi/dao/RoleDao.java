package com.shendehaizi.dao;

import com.shendehaizi.model.RoleModel;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class RoleDao extends MyBatisDao<RoleModel> {
    public RoleModel getRoleInfo(Map param){
        return this.findByUniqueIndex(param);
    }

}
