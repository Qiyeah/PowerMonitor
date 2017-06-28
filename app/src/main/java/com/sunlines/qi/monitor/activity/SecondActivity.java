package com.sunlines.qi.monitor.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sunlines.qi.monitor.R;
import com.sunlines.qi.monitor.adapter.SecondListAdapter;
import com.sunlines.qi.monitor.dao.impl.EnergyDaoImpl;
import com.sunlines.qi.monitor.dao.impl.PowerUsageEffectivenessDaoImpl;
import com.sunlines.qi.monitor.entity.Energy;
import com.sunlines.qi.monitor.entity.PowerUsageEffectiveness;
import com.sunlines.qi.monitor.utils.DBUtils;
import com.sunlines.qi.monitor.utils.DataFormat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * Created by temporary on 2017-01-17.
 */

public class SecondActivity extends BaseActivity {
    private static final String TAG = "TAG";
    /**
     *
     */
    private String CRLF = "\r\n";
    /**
     *
     */
    private TextView pueTitle, pueValue;
    private TextView totalEnergy, itEnergy;
//    private Button dayPue, monthPue, yearPue, accumDayPue, accumMonthPue, accumYearPue;
    private Button[] pueBtns = new Button[6];
    private ImageView mImageView;
    /**
     *
     */
    private int index = 0;
    private String[] fileNames = {"","bg_info.png", "bg_power.png", "bg_saving.png", "", "bg_support.png"};
    private String[] titles = new String[]{"当日PUE值", "当月PUE值", "当年PUE值",
            "24小时累计PUE值", "30天累计PUE值", "365天累计PUE值"};
    private String[] pueValues = new String[]{"0.000", "0.000", "0.000", "0.000", "0.000", "0.000"};
    private String[][] energyValues = new String[][]{{"0.000", "0.000"},
            {"0.000", "0.000"},
            {"0.000", "0.000"},
            {"0.000", "0.000"},
            {"0.000", "0.000"},
            {"0.000", "0.000"}};

    /**
     *
     */
    private int pageIndex = 0;
    /**
     *
     */
    private View contentView = null;
    private ListView mListView;
    private FrameLayout mContainer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        pageIndex = getIntent().getIntExtra("extraValue", 0);
        initUtils();
        updateViewData();
        initView();
        setAdapter();
        setListener();
        initTimerTaskParameters();
    }

    @Override
    protected void initUtils() {

    }

    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.second_lv_list);

        mContainer = (FrameLayout) findViewById(R.id.second_frame_container);
        updateContainerView();
    }

    @Override
    protected void updateView() {
        if (4 == pageIndex) {
            pueTitle.setText(titles[index]);
            pueValue.setText(pueValues[index]);
            for (int i = 0; i < pueBtns.length; i++) {

                Log.e(TAG, "updateView: "+pueValues[i] );
                pueBtns[i].setText(CRLF + pueValues[i]);
            }
            totalEnergy.setText(energyValues[index][0]);
            itEnergy.setText(energyValues[index][1]);
        }
    }

    @Override
    protected void updateViewData() {
        if (4 == pageIndex) {
            List<Energy> energies = EnergyDaoImpl.listEnergy();
            for (Energy energy : energies) {
                if (energy.getType() == 1){
                    energyValues[0][0] =  DataFormat.double2Str(energy.getValue());
                }else if (energy.getType() == 2){
                    energyValues[0][1] =  DataFormat.double2Str(energy.getValue());
                }else if (energy.getType() == 3){
                    energyValues[1][0] =  DataFormat.double2Str(energy.getValue());
                }else if (energy.getType() == 4){
                    energyValues[1][1] =  DataFormat.double2Str(energy.getValue());
                }else if (energy.getType() == 5){
                    energyValues[2][0] =  DataFormat.double2Str(energy.getValue());
                }else if (energy.getType() == 6){
                    energyValues[2][1] =  DataFormat.double2Str(energy.getValue());
                }else if (energy.getType() == 7){
                    energyValues[3][0] =  DataFormat.double2Str(energy.getValue());
                }else if (energy.getType() == 8){
                    energyValues[3][1] =  DataFormat.double2Str(energy.getValue());
                }else if (energy.getType() == 9){
                    energyValues[4][0] =  DataFormat.double2Str(energy.getValue());
                }else if (energy.getType() == 10){
                    energyValues[4][1] =  DataFormat.double2Str(energy.getValue());
                }else if (energy.getType() == 11){
                    energyValues[5][0] =  DataFormat.double2Str(energy.getValue());
                }else if (energy.getType() == 12){
                    energyValues[5][1] =  DataFormat.double2Str(energy.getValue());
                }
            }
           /* if (null != energies && 0 < energies.size()) {
                for (int i = 0; i < energyValues.length; i++) {
                    for (int j = 0; j < energyValues[i].length; j++) {
                        energyValues[i][j] = DataFormat.double2Str(energies.get(2 * i + j).getValue());
                        //Log.e("TAG", "energyValues[" + i + "][" + j + "] = " + energyValues[i][j]);
                    }
                }
            }*/
            List<PowerUsageEffectiveness> pues = PowerUsageEffectivenessDaoImpl.listPUE();
            if (null != pues && 0 < pues.size()) {
                for (int i = 0; i < pueValues.length; i++) {
                    PowerUsageEffectiveness pue = pues.get(i);
                    if (pue.getType() == DBUtils.PUE_DAY) {
                        pueValues[0]=DataFormat.float2Str(pue.getValue());
                    }else  if (pue.getType() == DBUtils.PUE_MON) {
                        pueValues[1]=DataFormat.float2Str(pue.getValue());
                    }else  if (pue.getType() == DBUtils.PUE_YEAR) {
                        pueValues[2]=DataFormat.float2Str(pue.getValue());
                    }else  if (pue.getType() == DBUtils.PUE_PAST_DAY) {
                        pueValues[3]=DataFormat.float2Str(pue.getValue());
                    }else  if (pue.getType() == DBUtils.PUE_PAST_MON) {
                        pueValues[4]=DataFormat.float2Str(pue.getValue());
                    }else  if (pue.getType() == DBUtils.PUE_PAST_YEAR) {
                        pueValues[5]= DataFormat.float2Str(pue.getValue());
                    }
                    //pueValues[i] = DataFormat.float2Str(pues.get(i).getValue());
//                    Log.e("TAG", "pueValues[" + i + "] = " + pueValues[i]);
                }
            }
        }
    }

    @Override
    protected void setListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
                pageIndex = i;
                // Toast.makeText(SecondActivity.this, "pageIndex = "+pageIndex, Toast.LENGTH_SHORT).show();
                if(0 == i){
                    finish();
                    System.gc();
                }else {
                    updateContainerView();
                }
            }
        });
    }

    @Override
    protected void setAdapter() {
        SecondListAdapter adapter = new SecondListAdapter(this);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void initTimerTaskParameters() {
        setStart(0);
        setDuration(500 * 3);
        doTask();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.gc();
        }
        return true;
    }

    Bitmap bitmap = null;

    private void updateContainerView() {
        // Log.e("TAG", "pageIndex = " + pageIndex);
        if (null != bitmap) {
            bitmap = null;
        }
        if (null != mImageView) {
            mImageView = null;
        }
        if (null != contentView) {
            contentView = null;
        }

       /* Toast.makeText(this, "bitmap = null?" + (bitmap == null)
                +"\nmImageView = null?" + (mImageView == null)+
                "\ncontentView = null?" + (contentView == null), Toast.LENGTH_SHORT).show();*/
        if (4 != pageIndex) {
//                ImageView imageView = null;
            contentView = LayoutInflater.from(this).inflate(R.layout.layout_second_img, null);
            InputStream is = null;
            mImageView = (ImageView) contentView.findViewById(R.id.second_img_show);
            try {

                is = getAssets().open(fileNames[pageIndex]);
                bitmap = BitmapFactory.decodeStream(is);
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != is) {
                        is.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {

            contentView = LayoutInflater.from(this).inflate(R.layout.layout_second_pue, null);
            pueTitle = (TextView) contentView.findViewById(R.id.second_tv_pueTitle);
            pueValue = (TextView) contentView.findViewById(R.id.second_tv_pueValue);
            totalEnergy = (TextView) contentView.findViewById(R.id.second_tv_totalEnergy);
            itEnergy = (TextView) contentView.findViewById(R.id.second_tv_itEnergy);

            pueBtns[0] = (Button) contentView.findViewById(R.id.second_btn_dayPue);
            pueBtns[1] = (Button) contentView.findViewById(R.id.second_btn_monthPue);
            pueBtns[2] = (Button) contentView.findViewById(R.id.second_btn_yearPue);
            pueBtns[3] = (Button) contentView.findViewById(R.id.second_btn_accum_dayPue);
            pueBtns[4] = (Button) contentView.findViewById(R.id.second_btn_accum_monthPue);
            pueBtns[5] = (Button) contentView.findViewById(R.id.second_btn_accum_yearPue);
            for (int i = 0; i < pueBtns.length; i++) {
                final int finalI = i;
                pueBtns[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        index = finalI;
                        updateView();
                    }
                });
            }
        }
        mContainer.removeAllViews();
        mContainer.addView(contentView);
        updateView();
    }
}
