package com.example.a96jsa.chronos_app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kevin on 4/29/2018.
 */

public class TimerService extends Service implements Parcelable{
    private Context context;
    private long tstart;
    private long tstop;
    private long elapsedTime;
    private long savedTime;
    private static Timer timer = new Timer();
    private int elapsedTimeSeconds;

    private boolean isCounting;
    private boolean isPlaying;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        startService();
        tstart = System.currentTimeMillis();
    }

    private void startService() {
        timer.schedule(new mainTask(),0,300);
        isCounting = true;
        isPlaying = true;

    }
    boolean isCounting(){
        return isCounting;
    }
    boolean isPlaying(){return  isPlaying;}
    void resumeService(){
        tstart = System.currentTimeMillis();
        timer = new Timer();
        isPlaying = true;
        timer.schedule(new mainTask(),0,300);

    }
    void stopService(){

        tstop = System.currentTimeMillis();
        elapsedTime = tstop - tstart;
        isPlaying = false;

        if(savedTime > 0){
            savedTime = elapsedTime + savedTime;
        }else {
            savedTime = elapsedTime;
        }
        //tstart = 1;


        elapsedTimeSeconds = (int)savedTime/1000;

        timer.cancel();
        Log.d("TIMER SERVICE","total time seconds: " + Integer.toString((elapsedTimeSeconds)));

    }
    int getElapsedTimeSeconds(){
        return elapsedTimeSeconds;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TIMER SERVICE", "SERVICE DESTOYED");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    private class mainTask extends TimerTask{
        public void run(){
            toatHandler.sendEmptyMessage(0);

        }

    }
    private final Handler toatHandler = new Handler(){
        @Override
        public void handleMessage(Message message){


            Log.d("TIMER SERVICE",  Integer.toString((int)System.currentTimeMillis()));
        }
    };
}
