package com.chappie.ladang.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.chappie.ladang.R;

public class ServiceSound extends Service {
    private boolean isRunning;
    private MediaPlayer player;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void setRunning(boolean z) {
        this.isRunning = z;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        this.player = MediaPlayer.create(getApplicationContext(), R.raw.cabana_sounds);
        this.player.setLooping(true);
        this.player.start();
        setRunning(true);
        return Service.START_NOT_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        this.player.stop();
        setRunning(false);
    }
}
