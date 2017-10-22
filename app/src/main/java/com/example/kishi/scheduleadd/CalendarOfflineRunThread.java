package com.example.kishi.scheduleadd;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class CalendarOfflineRunThread extends Activity implements Runnable {
    /*Called when the activity is first created.*/

    Thread thread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalendarOfflineRunThread.this.setContentView(R.layout.activity_main);

        thread = new Thread(CalendarOfflineRunThread.this);
        thread.start();
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
        }
    };

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            Thread.sleep(1000);
            Message message=new Message();
            message.what=1;
            handler.sendMessage(message);//發送消息

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
