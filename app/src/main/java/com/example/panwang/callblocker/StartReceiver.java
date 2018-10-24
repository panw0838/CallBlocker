package com.example.panwang.callblocker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, MainActivity.class);
        context.startService(service);
        Log.v("TAG", "开机自动服务自动启动.....");
        //启动应用，参数为需要自动启动的应用的包名
        //Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        //context.startActivity(intent);
    }
}
