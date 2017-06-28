package com.sunlines.qi.monitor.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.sunlines.qi.monitor.R;
import com.sunlines.qi.monitor.adapter.CommErrorAdapter;

/**
 * Created by temporary on 2017-01-16.
 */

public class CommErrorDialog {
    private AlertDialog.Builder builder = null;
    private AlertDialog dialog = null;
    private Context mContext = null;
    private View view = null;
    private ListView listView;
    private Button okBtn, cancelBtn;


    public CommErrorDialog(Context context) {
        mContext = context;
        initContentView();
        initDialog();
        setAdapter();
        setBtnClickListener();
    }

    private void setAdapter() {
        listView.setAdapter(new CommErrorAdapter(mContext));
    }

    private void setBtnClickListener() {
        okBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 // TODO: 2017-01-11
                 dialog.cancel();
                 dialog.dismiss();
             }
         });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
    }

    private void initContentView() {
        if (null == view) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_conn_error, null);
            listView = (ListView) view.findViewById(R.id.layout_main_conn_error_list);
            okBtn = (Button) view.findViewById(R.id.layout_main_conn_error_ok);
            cancelBtn = (Button) view.findViewById(R.id.layout_main_conn_error_cancel);
        }
    }

    private void initDialog() {
        if (null == builder) {
            builder = new AlertDialog.Builder(mContext);
            dialog = builder.create();
            dialog.setView(view);
        }
    }
    public void show(){
        dialog.show();
    }
}

