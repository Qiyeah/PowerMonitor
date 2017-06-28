package com.sunlines.qi.monitor.dao.impl;

import com.sunlines.qi.monitor.PowerMonitorApplication;
import com.sunlines.qi.monitor.dao.TemperatureDao;
import com.sunlines.qi.monitor.entity.Temperature;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by temporary on 2017-01-16.
 */

public class TemperatureDaoImpl {
    public static Temperature findTemperature(long fk, int rid){
        QueryBuilder<Temperature> qb = PowerMonitorApplication.getInstance().getTemperatureDao().queryBuilder();
        qb.where(TemperatureDao.Properties.Fk.eq(fk));
        qb.where(TemperatureDao.Properties.Rid.eq(rid));
        List<Temperature> list = qb.list();
        if (null !=list&& 0<list.size() ){
            return list.get(0);
        }
        return null;
    }
    public static float findTemperatureValue(long fk, int rid){
        QueryBuilder<Temperature> qb = PowerMonitorApplication.getInstance().getTemperatureDao().queryBuilder();
        qb.where(TemperatureDao.Properties.Fk.eq(fk));
        qb.where(TemperatureDao.Properties.Rid.eq(rid));
        List<Temperature> list = qb.list();
        if (null !=list&& 0<list.size() ){
            return list.get(0).getTemp();
        }
        return 0.0f;
    }
    public static void updateTemperatureTable(Temperature temperature){
        TemperatureDao dao = PowerMonitorApplication.getInstance().getTemperatureDao();
        QueryBuilder<Temperature> qb = dao.queryBuilder();
        qb.where(TemperatureDao.Properties.Fk.eq(temperature.getFk()));
        qb.where(TemperatureDao.Properties.Rid.eq(temperature.getRid()));
        List<Temperature> list = qb.list();
        if (null != list && 0 <list.size()){
            Temperature temp = list.get(0);
            temp.setTemp(temperature.getTemp());
            dao.update(temp);
        }else {
            dao.insert(temperature);
        }
    }

}
