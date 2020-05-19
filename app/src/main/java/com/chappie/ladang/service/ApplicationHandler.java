package com.chappie.ladang.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

public class ApplicationHandler implements Application.ActivityLifecycleCallbacks, ComponentCallbacks {
    private static final String TAG = "Astronot";
    private Context context;

    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
    }

    public void onConfigurationChanged(@NonNull Configuration configuration) {
    }

    public void onLowMemory() {
    }

    ApplicationHandler(Context context2) {
        this.context = context2;
    }

    public void onActivityCreated(@NonNull Activity activity, Bundle bundle) {
        Log.d(TAG, "onCreate");
    }

    public void onActivityStarted(@NonNull Activity activity) {
        Log.d(TAG, "onStarted");
        if (!isPlay()) {
            Context context2 = this.context;
            context2.startService(new Intent(context2, ServiceSound.class));
        }
    }

    public void onActivityResumed(@NonNull Activity activity) {
        Log.d(TAG, "onResume");
    }

    public void onActivityPaused(@NonNull Activity activity) {
        Log.d(TAG, "onPaused");
    }

    public void onActivityStopped(@NonNull Activity activity) {
        Log.d(TAG, "onStopped");
    }

    public void onActivityDestroyed(@NonNull Activity activity) {
        Log.d(TAG, "onDestroyed");
    }

    private boolean isPlay() {
        ActivityManager manager = (ActivityManager) this.context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (ServiceSound.class.getName().equals(serviceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
