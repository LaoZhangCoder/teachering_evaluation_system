package com.shendehaizi.dao;

import com.shendehaizi.model.UserActivationModel;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserActivationDao extends MyBatisDao<UserActivationModel> {
}
