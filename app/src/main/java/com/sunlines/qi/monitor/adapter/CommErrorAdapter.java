package com.sunlines.qi.monitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunlines.qi.monitor.R;
import com.sunlines.qi.monitor.dao.impl.CommErrorDaoImpl;
import com.sunlines.qi.monitor.entity.CommError;

import java.util.List;

/**
 * Created by temporary on 2017-01-16.
 */

public class CommErrorAdapter extends BaseAdapter {
    private List<CommError> mCommErrors = null;
    private Context mContext = null;
    public CommErrorAdapter(Context context) {
        mContext = context;
        loadLocalDatabase();
    }
    private void loadLocalDatabase() {
        if (null == mCommErrors) {
            mCommErrors = CommErrorDaoImpl.listCommError();
        }
    }
    @Override
    public int getCount() {
        return mCommErrors.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ErrorViewTag tag = null;
        if (null == convertView) {
            tag = new ErrorViewTag();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_main_conn_error_item,null);
            tag.icon = (ImageView) convertView.findViewById(R.id.main_conn_item_icon);
            tag.name = (TextView) convertView.findViewById(R.id.main_conn_item_name);
            tag.state = (ImageView) convertView.findViewById(R.id.main_conn_item_state);
            convertView.setTag(tag);
        }
        tag = (ErrorViewTag) convertView.getTag();
        if (mCommErrors.get(position).getErrorName().startsWith("ACT")){
            tag.icon.setImageResource(R.drawable.device02);
            tag.name.setText(mCommErrors.get(position).getMessage());
        }else if (mCommErrors.get(position).getErrorName().startsWith("AC0")){
            tag.icon.setImageResource(R.drawable.device01);
            tag.name.setText(mCommErrors.get(position).getMessage());
        }else if (mCommErrors.get(position).getErrorName().startsWith("E")){
            tag.icon.setImageResource(R.drawable.device03);
            tag.name.setText(mCommErrors.get(position).getMessage());
        }
        CommError error = mCommErrors.get(position);
        if (error.getType()>0){
            tag.state.setImageResource(R.drawable.light_red);
        }else {
            tag.state.setImageResource(R.drawable.light_green);
        }
        return convertView;
    }
    class ErrorViewTag {
        ImageView icon;
        TextView name;
        ImageView state;
    }
}

