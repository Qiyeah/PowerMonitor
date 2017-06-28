package com.sunlines.qi.monitor.dao.impl;

import com.sunlines.qi.monitor.PowerMonitorApplication;
import com.sunlines.qi.monitor.dao.CommErrorDao;
import com.sunlines.qi.monitor.entity.CommError;
import com.sunlines.qi.monitor.utils.IDUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Date;
import java.util.List;

/**
 * Created by temporary on 2017-01-16.
 */

public class CommErrorDaoImpl {
    public static void addCommError(CommError error){
        if (!isExist(error)){
            PowerMonitorApplication.getInstance().getCommErrorDao().insert(error);
        }else {
            PowerMonitorApplication.getInstance().getCommErrorDao().update(error);
        }
    }
    public static void delCommError(CommError error){
        CommErrorDao dao  =PowerMonitorApplication.getInstance().getCommErrorDao();
        QueryBuilder<CommError> qb = dao.queryBuilder();
        qb.where(CommErrorDao.Properties.ErrorName.eq(error.getErrorName()));
        List<CommError> list = qb.list();
        if (null != list && 0 <list.size()){
            dao.delete(list.get(0));
        }
    }
    public static void updateCommError(CommError error){
        CommErrorDao dao  =PowerMonitorApplication.getInstance().getCommErrorDao();
        QueryBuilder<CommError> qb =dao.queryBuilder();
        qb.where(CommErrorDao.Properties.ErrorName.eq(error.getErrorName()));
        List<CommError> list = qb.list();
        if (null != list && 0 <list.size()){
            CommError temp = list.get(0);
            error.setId(temp.getId());
            dao.update(error);
        }else {
            dao.insert(error);
        }
    }
    public static void updateCommError(String name,int type){
        CommErrorDao dao  =PowerMonitorApplication.getInstance().getCommErrorDao();
        QueryBuilder<CommError> qb =dao.queryBuilder();
        qb.where(CommErrorDao.Properties.ErrorName.eq(name));
        List<CommError> list = qb.list();
        if (null != list && 0 <list.size()){
            CommError temp = list.get(0);
            temp.setType(type);
            dao.update(temp);
        }else {
            dao.insert(new CommError(IDUtils.generateId(),type,name,"",new Date()));
        }
    }
    public static void disConnectServer(){
        CommErrorDao dao  =PowerMonitorApplication.getInstance().getCommErrorDao();
        QueryBuilder<CommError> qb =dao.queryBuilder();
        List<CommError> list = qb.list();
        if (null != list && 0 <list.size()){
            for (CommError error : list) {
                //Log.e("TAG",error.getErrorName()+"---"+error.getType()+"---"+error.getMessage()+"---"+error.getDt()+"---"+error.getId());
                error.setType(3);
                dao.update(error);
            }
        }
    }
    public static boolean isExist(CommError error){
        QueryBuilder<CommError> qb = PowerMonitorApplication.getInstance().getCommErrorDao().queryBuilder();
        qb.where(CommErrorDao.Properties.ErrorName.eq(error.getErrorName()));
        List<CommError> list = qb.list();
        if (null != list && 0 <list.size()){
            return true;
        }
        return false;
    }
    public static List<CommError> listCommError(){
        QueryBuilder<CommError> qb = PowerMonitorApplication.getInstance().getCommErrorDao().queryBuilder();
        qb.orderAsc(CommErrorDao.Properties.ErrorName);
        List<CommError> list = qb.list();
        if (null != list && 0 <list.size()){
            return list;
        }
        return null;
    }
    public static boolean hasError(){
        QueryBuilder<CommError> qb = PowerMonitorApplication.getInstance().getCommErrorDao().queryBuilder();
        qb.where(CommErrorDao.Properties.Type.notEq(0));
        List<CommError> list = qb.list();
        if (null != list && 0 <list.size()){
            return true;
        }
        return false;
    }
}
