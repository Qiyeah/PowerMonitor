package com.sunlines.qi.monitor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sunlines.qi.monitor.activity.MainActivity;


/**
 * Created by temporary on 2016/10/26.
 */

public class AutoStartReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)){
            Intent autoStartActivity=new Intent(context,MainActivity.class);
            autoStartActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(autoStartActivity);
        }
    }
}
