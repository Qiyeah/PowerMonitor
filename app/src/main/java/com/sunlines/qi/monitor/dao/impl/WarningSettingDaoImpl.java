package com.sunlines.qi.monitor.dao.impl;

import android.database.Cursor;
import android.util.Log;

import com.sunlines.qi.monitor.PowerMonitorApplication;
import com.sunlines.qi.monitor.dao.WarningSettingDao;
import com.sunlines.qi.monitor.entity.WarningSetting;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by temporary on 2017-01-16.
 */

public class WarningSettingDaoImpl {
    public static long findId(long fk, int rid) {
        QueryBuilder<WarningSetting> qb = PowerMonitorApplication.getInstance().getWarningSettingDao().queryBuilder();
        qb.where(WarningSettingDao.Properties.Fk.eq(fk));
        qb.where(WarningSettingDao.Properties.Rid.eq(rid));
        List<WarningSetting> list = qb.list();
        return list.get(0).getId();
    }
    public static  WarningSetting findWarningSetting(long fk, int rid){
        QueryBuilder<WarningSetting> qb = PowerMonitorApplication.getInstance().getWarningSettingDao().queryBuilder();
        qb.where(WarningSettingDao.Properties.Fk.eq(fk));
        qb.where(WarningSettingDao.Properties.Rid.eq(rid));
        return qb.list().get(0);
    }
    public static  float findSettingValue(long fk, int rid){
        QueryBuilder<WarningSetting> qb = PowerMonitorApplication.getInstance().getWarningSettingDao().queryBuilder();
        qb.where(WarningSettingDao.Properties.Fk.eq(fk));
        qb.where(WarningSettingDao.Properties.Rid.eq(rid));
        return qb.list().get(0).getTemp();
    }
    public static  int findSettingState(long fk, int rid){
        QueryBuilder<WarningSetting> qb = PowerMonitorApplication.getInstance().getWarningSettingDao().queryBuilder();
        qb.where(WarningSettingDao.Properties.Fk.eq(fk));
        qb.where(WarningSettingDao.Properties.Rid.eq(rid));
        return qb.list().get(0).getState();
    }
    public static  void addWarningSetting(WarningSetting setting){
        PowerMonitorApplication.getInstance().getWarningSettingDao().insert(setting);
    }
    public static  void updateWarningSetting(WarningSetting setting){
        PowerMonitorApplication.getInstance().getWarningSettingDao().update(setting);
    }
    public static  List<WarningSetting> findWarnins(){
        QueryBuilder<WarningSetting> qb = PowerMonitorApplication.getInstance().getWarningSettingDao().queryBuilder();
        qb.whereOr(WarningSettingDao.Properties.State.eq(1),WarningSettingDao.Properties.State.eq(3));
        return qb.list();
    }
    public static  List<WarningSetting> findAllWarninOuts(){
        QueryBuilder<WarningSetting> qb = PowerMonitorApplication.getInstance().getWarningSettingDao().queryBuilder();
        qb.where(WarningSettingDao.Properties.Rid.ge(3));

        return qb.list();
    }
    public static  List<WarningSetting> findAllWarninIns(){
        QueryBuilder<WarningSetting> qb = PowerMonitorApplication.getInstance().getWarningSettingDao().queryBuilder();
        qb.where(WarningSettingDao.Properties.Rid.le(2));

        return qb.list();
    }
    public static  void updateAllSettingTempIns(float tempIn){
        String sql ="update "+WarningSettingDao.TABLENAME
                +" set "+WarningSettingDao.Properties.Temp.columnName
                +" = ? where "+WarningSettingDao.Properties.Rid.columnName
                +" <= ? ";
        PowerMonitorApplication.getInstance().getWarningSettingDao().getDatabase().rawQuery(sql,new String[]{String.valueOf(tempIn)
                ,"2"});
    }
    public static  void updateAllSettingTempOuts(float tempOut){
        String sql ="update "+WarningSettingDao.TABLENAME
                +" set "+WarningSettingDao.Properties.Temp.columnName
                +" = "+tempOut+" where "+WarningSettingDao.Properties.Rid.columnName
                +" > 2 ";
        Log.e("TAG","tempOut = "+tempOut);
        Log.e("TAG","sql = "+sql);
        PowerMonitorApplication.getInstance().getWarningSettingDao().getDatabase().rawQuery(sql,null);
    }
    public static  List<Long> loadWarningList() {
        List<Long> list = new ArrayList<>();
        String sql = "select fk from " + WarningSettingDao.TABLENAME + " where " + WarningSettingDao.Properties.State.columnName + " = 2 ";
        Log.e("TAG","sql = "+sql);
        Cursor cursor = PowerMonitorApplication.getInstance().getDatabase().rawQuery(sql, null);
        if (cursor.moveToNext()) {
            long fk = cursor.getLong(cursor.getColumnIndex("fk"));
            list.add(fk);
        }
        return list;
    }
    public static  List<WarningSetting> findShieldWarnings(){
        QueryBuilder<WarningSetting> qb = PowerMonitorApplication.getInstance().getWarningSettingDao().queryBuilder();
        qb.where(WarningSettingDao.Properties.State.eq(2));
        return qb.list();
    }
    public static  List<WarningSetting> findAllSettings(){
        QueryBuilder<WarningSetting> qb = PowerMonitorApplication.getInstance().getWarningSettingDao().queryBuilder();
        return qb.list();
    }


    public static void updateWarningSettingTable(WarningSetting setting){
        WarningSettingDao dao = PowerMonitorApplication.getInstance().getWarningSettingDao();
        QueryBuilder<WarningSetting> qb = dao.queryBuilder();
        qb.where(WarningSettingDao.Properties.Fk.eq(setting.getFk()));
        qb.where(WarningSettingDao.Properties.Rid.eq(setting.getRid()));
        List<WarningSetting> list = qb.list();
        if (null != list && 0 <list.size()){
            WarningSetting temp = list.get(0);
            temp.setTemp(setting.getTemp());
            dao.update(temp);
        }else {
            dao.insert(setting);
        }
    }
}
