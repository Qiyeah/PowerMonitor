package com.sunlines.qi.monitor.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sunlines.qi.monitor.utils.DBUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by temporary on 2017-01-18.
 */

public class ServerService extends Service {
    int count = 1;
    DBUtils utils = null;
    Timer timer;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Log.e("TAG", "access to server database : " + (count++));
            if (null == utils) {
                utils = new DBUtils(getApplicationContext());
            }
            utils.queryServerPUEData();
            utils.queryServerEnergyData();
            //utils.queryServerTemperatureData();
           // utils.queryServerAllDeviceState();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(task, 1000 , 1000 * 3);

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        task.cancel();
        timer.cancel();
        task = null;
        timer = null;
        super.onDestroy();
    }
}
