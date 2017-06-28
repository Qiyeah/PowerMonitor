package com.sunlines.qi.monitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunlines.qi.monitor.R;

import java.io.InputStream;

/**
 * Created by temporary on 2017-01-18.
 */

public class SecondListAdapter extends BaseAdapter {
    private int[] resIds = {R.drawable.icon_back,R.drawable.icon_info,R.drawable.icon_power,R.drawable.icon_saving,R.drawable.icon_pue,R.drawable.icon_support};
    private String[] titles = {"返回","机房简介","机房供电","机房节能","PUE值","技术支持"};
    private Context mContext;
    private InputStream is = null;

    public SecondListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return resIds.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        ViewHolder holder = null;
        if (null == view){
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_second_list_item,null);
            holder.img = (ImageView) view.findViewById(R.id.second_list_item_img);
            holder.text = (TextView) view.findViewById(R.id.second_list_item_text);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.img.setImageResource(resIds[i]);
        holder.text.setText(titles[i]);
        return view;
    }
    class ViewHolder{
        ImageView img;
        TextView text;
    }
}
