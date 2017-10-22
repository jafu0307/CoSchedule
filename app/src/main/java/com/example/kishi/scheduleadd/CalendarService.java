package com.example.kishi.scheduleadd;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by chiafu on 22/10/2017.
 */

public class CalendarService extends Service {

    private static final String TAG = "com.example.kishi.scheduleadd";

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /* 只生成一個thread
        Log.i(TAG, "Service onStartCommand");

        for (int i = 0; i < 3; i++)
        {
            long endTime = System.currentTimeMillis() + 10*1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) { }
                }
            }
            Log.i(TAG, "Service running");
        }
        return Service.START_STICKY; //Service會停留在started state，但intent資料不會被保留*/
        Log.i(TAG, "Service onStartCommand " + startId);

        final int currentId = startId;

        Runnable r = new Runnable() {
            public void run() {

                for (int i = 0; i < 3; i++)
                {
                    long endTime = System.currentTimeMillis() + 10*1000;

                    while (System.currentTimeMillis() < endTime) {
                        synchronized (this) {
                            try {
                                wait(endTime -
                                        System.currentTimeMillis());
                            } catch (Exception e) {
                            }
                        }
                    }
                    Log.i(TAG, "Service running " + currentId);
                }
                stopSelf();
            }
        };

        Thread t = new Thread(r);
        t.start();
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Service onDestroy");
    }
}
