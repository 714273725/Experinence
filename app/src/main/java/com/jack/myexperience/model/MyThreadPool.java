package com.jack.myexperience.model;
import com.jack.myexperience.datainterface.BaseThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Created by Administrator on 2016/2/26 0026.
 */
public class MyThreadPool implements BaseThreadPool {
    ExecutorService mNowPool;
    ExecutorService mFreePool;
    List<CancelableTask> mNowList;
    public static MyThreadPool mPool;
    {
        mNowPool= Executors.newCachedThreadPool();
        mFreePool = Executors.newFixedThreadPool(3);
        mNowList=new ArrayList<>();
    }
    public static MyThreadPool getInstance(){
        if(null==mPool){
            mPool=new MyThreadPool();
        }
        return mPool;
    }

    private MyThreadPool() {
    }

    @Override
    public void executeNow(Runnable now) {
        mNowList.add((CancelableTask) now);
        mNowPool.execute(now);
    }

    @Override
    public void executeWhenFree(Runnable free) {
        mFreePool.execute(free);
    }

    @Override
    public void cancelAll() {

    }
    @Override
    public void cancelAllNow() {
        for(int i=0;i<mNowList.size();i++){
            mNowList.get(i).cancel();
        }
        mNowList.clear();
    }

    @Override
    public void cancelAllFree() {

    }
}
