package com.example.yerim;

import android.os.Message;
import android.os.Handler;

public class introThread extends Thread {
    private Handler handler;

    public introThread(Handler handler)
    {
        this.handler = handler;
    }

    @Override
    public void run() {
        Message msg = new Message();

        try {
            Thread.sleep(2000);   // time to show intro screen
            msg.what = 1;
            handler.sendEmptyMessage(msg.what);   // send message to finish intro screen
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
