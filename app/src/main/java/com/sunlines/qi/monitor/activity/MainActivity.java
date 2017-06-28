package com.sunlines.qi.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunlines.qi.monitor.R;
import com.sunlines.qi.monitor.dao.impl.CommErrorDaoImpl;
import com.sunlines.qi.monitor.dao.impl.GlobalParameterDaoImpl;
import com.sunlines.qi.monitor.dao.impl.PowerUsageEffectivenessDaoImpl;
import com.sunlines.qi.monitor.dialog.CommErrorDialog;
import com.sunlines.qi.monitor.dialog.ConfigDialog;
import com.sunlines.qi.monitor.entity.GlobalParameter;
import com.sunlines.qi.monitor.service.ServerService;
import com.sunlines.qi.monitor.utils.DBUtils;
import com.sunlines.qi.monitor.utils.DataFormat;
import com.sunlines.qi.monitor.utils.IDUtils;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     *
     */
    private Button infoBtn, powerBtn, savingBtn, honorBtn, supportBtn, pueBtn;
    private ImageView lightImg;
    private TextView configTv;
    /**
     *
     */
    Context mContext = null;
    DBUtils mUtils = null;
    Intent serviceIntent = null;
    /**
     *
     */
    private int extraValue = 0;
    private float day = 0.0f;
    boolean hasError = false;
    /**
     *
     */
    private GlobalParameter mGlobalParameter = null;
    /**
     *
     */
    private Timer mTimer = null;
    private TimerTask mTask = null;
    private Handler mHandler = null;

    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         *
         */
        initUtils();
        /**
         *
         */
        initLocalDatabase();
        /**
         * 初始化View
         */
        initView();
        /**
         *
         */
        updateView();
        /**
         *
         */
        setListener();
        /**
         *
         */
        startService(serviceIntent);
        /**
         *
         */
        doTimerTask();
    }

    private void setListener() {
        configTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConfigDialog(MainActivity.this).show();
            }
        });
        infoBtn.setOnClickListener(this);
        supportBtn.setOnClickListener(this);
        savingBtn.setOnClickListener(this);
        pueBtn.setOnClickListener(this);
        powerBtn.setOnClickListener(this);
        lightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommErrorDialog(MainActivity.this).show();
            }
        });
    }

    private void doTimerTask() {
        mTimer.schedule(mTask, 0, 1000 * 10);
    }

    private void initUtils() {
        serviceIntent = new Intent(this,ServerService.class);
        if (null == mContext) {
            mContext = getApplicationContext();
        }
        if (null == mUtils) {
            mUtils = new DBUtils(mContext);
        }
        if (null == mHandler) {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (0x1 == msg.what){
                        Log.e("TAG","update main pue ( pue = "+day+" )");
                        initLocalDatabase();
                        updateViewData();
                        updateView();
                    }
                }
            };
        }
        if (null == mTimer) {
            mTimer = new Timer();
        }
        if (null == mTask) {
            mTask = new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(0x1);
                }
            };
        }
    }

    private void initLocalDatabase() {
        mGlobalParameter = GlobalParameterDaoImpl.queryGlobalParameter();
        if (mGlobalParameter == null) {
            mGlobalParameter = new GlobalParameter(
                    IDUtils.generateId(),
                    "192.168.1.1",
                    1,
                    1
            );
            GlobalParameterDaoImpl.updateGlobalParameterTable(mGlobalParameter);
        }
        //mUtils.initCoordinateTable();
        mUtils.initCommErrorTable();
    }

    private void initView() {
        configTv = (TextView) findViewById(R.id.main_tv_config);
        infoBtn = (Button) findViewById(R.id.main_btn_info);
        powerBtn = (Button) findViewById(R.id.main_btn_power);
        savingBtn = (Button) findViewById(R.id.main_btn_saving);
        honorBtn = (Button) findViewById(R.id.main_btn_honor);
        supportBtn = (Button) findViewById(R.id.main_btn_support);
        pueBtn = (Button) findViewById(R.id.main_btn_pue);
        lightImg = (ImageView) findViewById(R.id.main_img_light);
    }

    private void updateViewData() {
        hasError = CommErrorDaoImpl.hasError();
        day = PowerUsageEffectivenessDaoImpl.getDayPUE(
                mGlobalParameter.getFloor(), mGlobalParameter.getRoom());
    }

    private void updateView() {
        pueBtn.setText("PUE = "+DataFormat.float2Str(day));
        if (hasError){
            lightImg.setImageResource(R.drawable.light_red);
        }else {
            lightImg.setImageResource(R.drawable.light_green);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,SecondActivity.class);

        if (view.getId() == configTv.getId()) {
            new ConfigDialog(MainActivity.this).show();
        } else if (view.getId() == infoBtn.getId()) {
            extraValue = 1;
        } else if (view.getId() == powerBtn.getId()) {
            extraValue = 2;
        } else if (view.getId() == savingBtn.getId()) {
            extraValue = 3;
        } else if (view.getId() == honorBtn.getId()) {
            extraValue = 6;
        } else if (view.getId() == supportBtn.getId()) {
            extraValue = 5;
        } else if (view.getId() == pueBtn.getId()) {
            extraValue = 4;
        } else if (view.getId() == lightImg.getId()) {
            new CommErrorDialog(MainActivity.this).show();
        }
        intent.putExtra("extraValue",extraValue);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
       // finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }

    @Override
    protected void onDestroy() {
        stopService(serviceIntent);
        serviceIntent = null;
        mTask.cancel();
        mTimer.cancel();
        super.onDestroy();

    }
}
