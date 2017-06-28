package com.sunlines.qi.monitor.dao.impl;

import android.database.Cursor;

import com.sunlines.qi.monitor.PowerMonitorApplication;
import com.sunlines.qi.monitor.dao.CoordinateDao;
import com.sunlines.qi.monitor.entity.Coordinate;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by temporary on 2017-01-16.
 */

public class CoordinateDaoImpl {
    public static void addCoordinate(Coordinate c) {
        PowerMonitorApplication.getInstance().getCoordinateDao().insert(c);
    }

    public static long findId(int row, int col) {
        QueryBuilder<Coordinate> qb = PowerMonitorApplication.getInstance().getCoordinateDao().queryBuilder();
        qb.where(CoordinateDao.Properties.Row.eq(row));
        qb.where(CoordinateDao.Properties.Col.eq(col));
        List<Coordinate> list = qb.list();
        if (null != list && 0< list.size()){
            return list.get(0).getId();
        }
        return -1;
    }

    public static Coordinate findCoordinate(int row, int col) {
        QueryBuilder<Coordinate> qb = PowerMonitorApplication.getInstance().getCoordinateDao().queryBuilder();
        qb.where(CoordinateDao.Properties.Row.eq(row));
        qb.where(CoordinateDao.Properties.Col.eq(col));
        List<Coordinate> list = qb.list();
        if (list.size() > 0 && null != list) {
            return list.get(0);
        } else return null;
    }

    public static Coordinate findCoordinates(long id) {
        QueryBuilder<Coordinate> qb = PowerMonitorApplication.getInstance().getCoordinateDao().queryBuilder();
        qb.where(CoordinateDao.Properties.Id.eq(id));
        return qb.list().get(0);
    }

    public static boolean isExist(String name) {
        QueryBuilder<Coordinate> qb = PowerMonitorApplication.getInstance().getCoordinateDao().queryBuilder();
        qb.where(CoordinateDao.Properties.SName.eq(name));
        List<Coordinate> list = qb.list();
        if (list.size() > 0 && list != null) {
            return true;
        }
        return false;
    }

    public static int existRows(String name) {
        QueryBuilder<Coordinate> qb = PowerMonitorApplication.getInstance().getCoordinateDao().queryBuilder();
        qb.where(CoordinateDao.Properties.SName.eq(name));
        List<Coordinate> list = qb.list();
        if (list.size() > 0 && list != null) {
            return list.size();
        }
        return 0;
    }

    public static int findMaxRowNum() {
        // Log.e("TAG","-->>查询最大的RowNum----------");
        Database qb = PowerMonitorApplication.getInstance().getCoordinateDao().getDatabase();
        String sql = "select max(" + CoordinateDao.Properties.Row.columnName + ") as max from " + CoordinateDao.TABLENAME;
        Cursor c = qb.rawQuery(sql, null);
        if (c.moveToNext()) {
            int max = c.getInt(c.getColumnIndex("max"));
            /*Log.e("TAG", "----->>max = " + max);
            Log.e("TAG", "sql = " + sql);*/
            return max;
        }
        return 0;
    }

    public static int findMaxRowNumByName(String name) {
        // Log.e("TAG","-->>查询最大的RowNum----------");
        Database qb = PowerMonitorApplication.getInstance().getCoordinateDao().getDatabase();
        String sql = "select max(" + CoordinateDao.Properties.Row.columnName + ") as max from " + CoordinateDao.TABLENAME
                + " where " + CoordinateDao.Properties.Col.columnName + " = ? and "
                + CoordinateDao.Properties.SName.columnName + " = ?";
        ;
        Cursor c = qb.rawQuery(sql, new String[]{"0", name});
        if (c.moveToNext()) {
            int max = c.getInt(c.getColumnIndex("max"));
            /*Log.e("TAG", "----->>max = " + max);
            Log.e("TAG", "sql = " + sql);*/
            return max;
        }
        return 0;
    }

    public static int findMinRowNumByName(String name) {
        // Log.e("TAG","-->>查询最大的RowNum----------");
        Database qb = PowerMonitorApplication.getInstance().getCoordinateDao().getDatabase();
        String sql = "select min(" + CoordinateDao.Properties.Row.columnName + ") as min from " + CoordinateDao.TABLENAME
                + " where " + CoordinateDao.Properties.Col.columnName + " = ? and "
                + CoordinateDao.Properties.SName.columnName + " = ?";
        ;
        Cursor c = qb.rawQuery(sql, new String[]{"0", name});
        if (c.moveToNext()) {
            int min = c.getInt(c.getColumnIndex("min"));
            /*Log.e("TAG", "----->>max = " + max);
            Log.e("TAG", "sql = " + sql);*/
            return min;
        }
        return 0;
    }

    public static List<Long> findAllDeviceFKs() {
        List<Long> temp = new ArrayList<>();
        QueryBuilder<Coordinate> qb = PowerMonitorApplication.getInstance().getCoordinateDao().queryBuilder();
        qb.where(CoordinateDao.Properties.Col.notEq(0));
        List<Coordinate> list = qb.list();
        for (Coordinate c : list) {
            temp.add(c.getId());
        }
        return temp;
    }
    public static List<String> findAllDeviceNames(){
        List<String> names = new ArrayList<String>();
        CoordinateDao dao = PowerMonitorApplication.getInstance().getCoordinateDao();
        QueryBuilder<Coordinate> qb = dao.queryBuilder();
        qb.where(CoordinateDao.Properties.Col.eq(0));
        qb.distinct();
        List<Coordinate> list = qb.list();
        if (null != list && 0 <list.size()){
            for (Coordinate coordinate : list) {
                names.add(coordinate.getName());
            }
        }
        return null;
    }
}
