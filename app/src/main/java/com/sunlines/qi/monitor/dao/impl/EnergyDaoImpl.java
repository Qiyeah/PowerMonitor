package com.sunlines.qi.monitor.dao.impl;

import com.sunlines.qi.monitor.PowerMonitorApplication;
import com.sunlines.qi.monitor.dao.EnergyDao;
import com.sunlines.qi.monitor.entity.Energy;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by temporary on 2017-01-16.
 */

public class EnergyDaoImpl {
    public static void addEnergy(Energy energy) {
        Energy temp = findEnergy(energy);
        if (null == temp){
            PowerMonitorApplication.getInstance().getEnergyDao().insert(energy);
        }else {
            energy.setId(temp.getId());
            PowerMonitorApplication.getInstance().getEnergyDao().update(energy);
        }
    }
    public static Energy findEnergy(Energy energy) {
        QueryBuilder<Energy> qb = PowerMonitorApplication.getInstance().getEnergyDao().queryBuilder();
        qb.where(EnergyDao.Properties.Floor.eq(energy.getFloor()));
        qb.where(EnergyDao.Properties.Room.eq(energy.getRoom()));
        qb.where(EnergyDao.Properties.Type.eq(energy.getType()));
        List<Energy> list = qb.list();
        if (null != list && 0 < list.size()) {
            return list.get(0);
        }
        return null;
    }
    public static List<Energy> listEnergy() {
        EnergyDao dao = PowerMonitorApplication.getInstance().getEnergyDao();
        QueryBuilder<Energy> qb = dao.queryBuilder();
        List<Energy> list = qb.list();
        if (null != list && 0 < list.size()) {
            return list;
        }
        return null;
    }
}
