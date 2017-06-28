package com.sunlines.qi.monitor.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by temporary on 2017-01-17.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private Timer timer = null;
    private TimerTask task = null;
    private Handler handler = null;
    private long deplayed = 0;
    private long start = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        cancelTask();
        super.onDestroy();
    }
    protected abstract void initUtils();
    protected abstract void initView();
    protected abstract void updateView();
    protected abstract void updateViewData();
//    protected abstract void updateLocalDatabase();
    private int count = 0;
    protected void doTask(){
        if (null == handler){
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    updateViewData();
                    updateView();
                }
            };
        }
        if (null == task){
            task = new TimerTask() {
                @Override
                public void run() {
//                    Log.e("TAG"," count = "+(++count));
                    handler.sendEmptyMessage(0x1);
                }
            };
        }
        if (null == timer){
            timer = new Timer();
        }
        timer.schedule(task,start,deplayed);
    }
    protected abstract void setListener();
    protected abstract void setAdapter();
    protected void cancelTask(){
        if (null != task){
            task.cancel();
        }
        if (null != timer){
            timer.cancel();
        }
    }
    protected abstract void initTimerTaskParameters();

    public void setDuration(long deplayed) {
        this.deplayed = deplayed;
    }

    public void setStart(long start) {
        this.start = start;
    }
}
