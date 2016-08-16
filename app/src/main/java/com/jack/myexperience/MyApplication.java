package com.jack.myexperience;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jack.myexperience.model.MyThreadPool;

import java.util.LinkedList;
import java.util.List;

import ye.jian.ge.BaseContext;

/**
 * Created by Administrator on 2016/2/23 0023.
 */
public class MyApplication extends Application {
    private static MyThreadPool mThreadPool;
    private static MyApplication myApplication;
    private static LayoutInflater inflater;  //布局填充器
    private static List<Activity> mListTaskQueue;   //activity队列
    {
        mListTaskQueue=new LinkedList<>();
    }
    public static MyThreadPool executer(){
        if(null==mThreadPool){
            mThreadPool=MyThreadPool.getInstance();
        }
        return mThreadPool;
    }
    public static List<Activity> getListTaskQueue() {
        if(mListTaskQueue==null){
            mListTaskQueue=new LinkedList<>();
        }
        return mListTaskQueue;
    }

    /**
     * 获取一个布局填充器
     * @return
     */
    public static LayoutInflater getInflater(){
        if(inflater==null){
            inflater= (LayoutInflater) getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return inflater;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        BaseContext.initialize(this);
        Fresco.initialize(this);
    }
    public static Activity getTargetActivity(Class<? extends Activity> target){
        Activity targetActivity=null;
        for(int i=0;i< getListTaskQueue().size();i++){
            if(getListTaskQueue().get(i).getClass().equals(target)){
                targetActivity= getListTaskQueue().get(i);
            }
        }
        if(targetActivity==null){
            try {
                targetActivity=target.newInstance();
            } catch (InstantiationException e) {
                Log.d("App_getTargetAct", e.getMessage());
            } catch (IllegalAccessException e) {
                Log.d("App_getTargetAct", e.getMessage());
            }finally {
                return targetActivity;
            }
        }
        return targetActivity;
    }
    public static MyApplication getInstance(){
        if(myApplication==null){
            myApplication=new MyApplication();
        }
        return myApplication;
    }
    public void exitApp() {
        try {
            for (Activity activity : mListTaskQueue) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
