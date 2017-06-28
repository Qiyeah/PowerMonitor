package com.sunlines.qi.monitor.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.sunlines.qi.monitor.R;
import com.sunlines.qi.monitor.dao.impl.GlobalParameterDaoImpl;
import com.sunlines.qi.monitor.entity.GlobalParameter;
import com.sunlines.qi.monitor.utils.IDUtils;

/**
 * Created by temporary on 2017-01-16.
 */

public class ConfigDialog {
    /**
     *
     */
    private Context mContext;
    /**
     *
     */
    GlobalParameter param = null;
    /**
     *
     */
    private AlertDialog.Builder mBuilder = null;
    /**
     *
     */
    private View view = null;
    private EditText ipEt,floorEt,roomEt;

    public ConfigDialog(Context context) {
        mContext  = context;

        loadLocalDatabase();

        initContentView();

        initDialog();

        setBtnClickListener();
    }

    private void loadLocalDatabase() {
        param = GlobalParameterDaoImpl.queryGlobalParameter();
        if (param == null) {
            param = new GlobalParameter(
                    IDUtils.generateId(),
                    "192.168.1.109",
                    1,
                    1
            );
            GlobalParameterDaoImpl.updateGlobalParameterTable(param);
        }
    }

    private void setBtnClickListener() {
        mBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialog.cancel();
            }
        });
        mBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ipStr = ipEt.getText().toString().trim();
                int floorInt = Integer.parseInt(floorEt.getText().toString().trim());
                int roomInt = Integer.parseInt(roomEt.getText().toString().trim());
                param = new GlobalParameter(
                        IDUtils.generateId(),
                        ipStr,
                        floorInt,
                        roomInt
                );
                GlobalParameterDaoImpl.updateGlobalParameterTable(param);
                dialog.dismiss();
                dialog.cancel();
            }
        });
    }

    private void initContentView() {
        if (null == view){
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_global_config, null);
            ipEt = (EditText) view.findViewById(R.id.ip);
            floorEt = (EditText) view.findViewById(R.id.floor);
            roomEt = (EditText) view.findViewById(R.id.room);
            ipEt.setText(param.getIp());
            floorEt.setText(String.valueOf(param.getFloor()));
            roomEt.setText(String.valueOf(param.getRoom()));
        }
    }

    private void initDialog() {
        mBuilder = new AlertDialog.Builder(mContext);
        mBuilder.setTitle(R.string.main_global_config_title);
        mBuilder.setView(view);
        mBuilder.setCancelable(false);
    }
    public void show(){
        mBuilder.show();
    }
}
