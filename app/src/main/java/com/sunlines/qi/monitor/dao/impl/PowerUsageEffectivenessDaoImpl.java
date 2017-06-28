package com.sunlines.qi.monitor.dao.impl;

import com.sunlines.qi.monitor.PowerMonitorApplication;
import com.sunlines.qi.monitor.dao.PowerUsageEffectivenessDao;
import com.sunlines.qi.monitor.entity.PowerUsageEffectiveness;
import com.sunlines.qi.monitor.utils.DBUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by temporary on 2017-01-16.
 */

public class PowerUsageEffectivenessDaoImpl {
    public static void addPowerUsageEffect(PowerUsageEffectiveness pue) {
        PowerUsageEffectiveness temp = findPowerUsageEffect(pue);
        if (null == temp) {
            PowerMonitorApplication.getInstance().getPowerUsageEffectivenessDao().insert(pue);
        } else {
            pue.setId(temp.getId());
            PowerMonitorApplication.getInstance().getPowerUsageEffectivenessDao().update(pue);
        }
    }

    public static PowerUsageEffectiveness findPowerUsageEffect(PowerUsageEffectiveness pue) {
        QueryBuilder<PowerUsageEffectiveness> qb = PowerMonitorApplication.getInstance().getPowerUsageEffectivenessDao().queryBuilder();
        qb.where(PowerUsageEffectivenessDao.Properties.Floor.eq(pue.getFloor()));
        qb.where(PowerUsageEffectivenessDao.Properties.Room.eq(pue.getRoom()));
        qb.where(PowerUsageEffectivenessDao.Properties.Type.eq(pue.getType()));
        List<PowerUsageEffectiveness> list = qb.list();
        if (null != list && 0 < list.size()) {
            return list.get(0);
        }
        return null;
    }

    public static boolean isExist(PowerUsageEffectiveness pue) {
        QueryBuilder<PowerUsageEffectiveness> qb = PowerMonitorApplication.getInstance().getPowerUsageEffectivenessDao().queryBuilder();
        qb.where(PowerUsageEffectivenessDao.Properties.Floor.eq(pue.getFloor()));
        qb.where(PowerUsageEffectivenessDao.Properties.Room.eq(pue.getRoom()));
        qb.where(PowerUsageEffectivenessDao.Properties.Type.eq(pue.getType()));
        List<PowerUsageEffectiveness> list = qb.list();
        if (null != list && 0 < list.size()) {
            return true;
        }
        return false;
    }

    public static List<PowerUsageEffectiveness> listPUE() {
        PowerUsageEffectivenessDao dao = PowerMonitorApplication.getInstance().getPowerUsageEffectivenessDao();
        QueryBuilder<PowerUsageEffectiveness> qb = dao.queryBuilder();
        List<PowerUsageEffectiveness> list = qb.list();
        if (null != list && 0 < list.size()) {
            return list;
        }
        return null;
    }

    public static float getDayPUE(int floor, int room) {
        //Log.e("TAG2","floor = "+floor+" room = "+room);
        PowerUsageEffectivenessDao dao = PowerMonitorApplication.getInstance().getPowerUsageEffectivenessDao();
        QueryBuilder<PowerUsageEffectiveness> qb = dao.queryBuilder();
        qb.where(PowerUsageEffectivenessDao.Properties.Floor.eq(floor));
        qb.where(PowerUsageEffectivenessDao.Properties.Room.eq(room));
        qb.where(PowerUsageEffectivenessDao.Properties.Type.eq(DBUtils.PUE_DAY));
        List<PowerUsageEffectiveness> list = qb.list();
        if (null != list && 0 < list.size()) {
            return list.get(0).getValue();
        }
        return 0.0f;
    }
}
