package com.jack.myexperience.model;

import android.os.Handler;
import android.os.Message;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/2/26 0026.
 */
public class CancelableTask implements Runnable {
    Handler mHandler;
    int mPosition;
    int mProgress;
    public CancelableTask(Handler mHandler, int mPosition) {
        this.mHandler = mHandler;
        this.mPosition = mPosition;
    }
    private volatile Boolean flag;
    {
        flag=true;
    }
    @Override
    public void run() {
        synchronized (flag){
            while (flag){
                try {
                    if(mProgress<10){
                        mProgress++;
                        Message message=Message.obtain();
                        message.what=mPosition;
                        message.arg1=mProgress*10;
                        mHandler.sendMessage(message);
                        Thread.sleep(1000);
                        while (mProgress>=10){
                            flag=false;
                            finish();
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void finish(){
        mProgress=0;
        Message message=Message.obtain();
        message.what=mPosition;
        message.arg1=mProgress*10;
        mHandler.sendMessage(message);
    }
    public void cancel(){
        this.flag=false;
        mProgress=0;
        Message message=Message.obtain();
        message.what=mPosition;
        message.arg1=mProgress*10;
        mHandler.sendMessage(message);
    }
    public boolean isRunning(){
        return flag;
    }
    public void reRun(){
        if(isRunning()){
            mProgress=0;
        }
    }
}
