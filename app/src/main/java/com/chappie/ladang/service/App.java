package com.chappie.ladang.service;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class App extends Application {
    private static final String TAG = "Astronot";

    public void onCreate() {
        super.onCreate();
        ApplicationHandler applicationHandler = new ApplicationHandler(getApplicationContext());
        registerActivityLifecycleCallbacks(applicationHandler);
        registerComponentCallbacks(applicationHandler);
        if (!isPlay()) {
            startService(new Intent(this, ServiceSound.class));
        }
    }

    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate");
    }

    public void onTrimMemory(int i) {
        super.onTrimMemory(i);
        stopService(new Intent(this, ServiceSound.class));
        Log.d(TAG, "onTrimMemory");
    }

    private boolean isPlay() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (ServiceSound.class.getName().equals(serviceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
