package com.example.kishi.scheduleadd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;


public class CalendarAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // For our recurring task, we'll just display a message
        Log.d("TIME: ", "Done");
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
    }
}
