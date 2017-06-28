package com.sunlines.qi.monitor;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.sunlines.qi.monitor.dao.DaoMaster;
import com.sunlines.qi.monitor.dao.DaoSession;


/**
 * Created by temporary on 2016-12-24.
 */

public class PowerMonitorApplication extends Application {
    private static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
    }
    private void initDatabase(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"Sunlines.db",null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        daoSession = master.newSession();
    }
    public static DaoSession getInstance(){
        return daoSession;
    }
}
