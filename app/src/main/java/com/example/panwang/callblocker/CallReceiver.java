package com.example.panwang.callblocker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.app.Service;
import android.util.Log;
import android.os.IBinder;
import java.lang.reflect.Method;
import com.android.internal.telephony.ITelephony;

public class CallReceiver extends BroadcastReceiver {
    String TAG = "PhoneReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
        }
        else {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    PhoneStateListener listener;
    {
        listener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                // state 当前状态 incomingNumber,貌似没有去电的API
                super.onCallStateChanged(state, incomingNumber);
                switch (state) {
                    //手机空闲了
                    case TelephonyManager.CALL_STATE_IDLE:
                        break;
                    //电话被挂起
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        break;
                    // 当电话呼入时
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.e(TAG, "来电号码是：" + incomingNumber);
                        // 如果该号码属于黑名单
                        if (incomingNumber.equals("*********")) {
                            // TODO:如果是黑名单，就进行屏蔽
                            stopCall();
                        }
                        break;
                }
            }
        };
    }

    public void stopCall() {
        try {
            Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            // 获取远程TELEPHONY_SERVICE的IBinder对象的代理
            IBinder binder = (IBinder) method.invoke(null, new Object[] { "phone" });
            // 将IBinder对象的代理转换为ITelephony对象
            ITelephony telephony = ITelephony.Stub.asInterface(binder);
            // 挂断电话
            telephony.endCall();
            //telephony.cancelMissedCallsNotification();
        } catch (Exception e) {
            Log.e(TAG, "error" + e.toString());
        }
    }
}
