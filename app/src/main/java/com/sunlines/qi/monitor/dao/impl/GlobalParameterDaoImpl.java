package com.sunlines.qi.monitor.dao.impl;

import com.sunlines.qi.monitor.PowerMonitorApplication;
import com.sunlines.qi.monitor.dao.GlobalParameterDao;
import com.sunlines.qi.monitor.entity.GlobalParameter;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by temporary on 2017-01-16.
 */

public class GlobalParameterDaoImpl {
    public static void updateGlobalParameterTable(GlobalParameter param){
        //Log.e("TAG2"," add to global parameter -->>ip = "+param.getIp()+" floor = "+param.getFloor()+" room = "+param.getRoom());
        GlobalParameterDao dao = PowerMonitorApplication.getInstance().getGlobalParameterDao();
        QueryBuilder<GlobalParameter> qb = dao.queryBuilder();
        List<GlobalParameter> list = qb.list();
        if (null !=  list && 0 <list.size()){
            param.setId(list.get(0).getId());
            dao.update(param);
        }else {
            dao.insert(param);
        }

    }
    public static GlobalParameter queryGlobalParameter(){
        GlobalParameterDao dao = PowerMonitorApplication.getInstance().getGlobalParameterDao();
        QueryBuilder<GlobalParameter> qb = dao.queryBuilder();
        List<GlobalParameter> list = qb.list();
        if (null !=  list && 0 <list.size()){
            return list.get(0);
        }else {
            return null;
        }
    }
}
