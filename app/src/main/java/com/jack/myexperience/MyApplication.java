package com.jack.myexperience;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.LayoutInflater;

import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.jack.myexperience.model.MyThreadPool;
import com.xycode.xylibrary.okHttp.Header;
import com.xycode.xylibrary.okHttp.OkHttp;
import com.xycode.xylibrary.okHttp.Param;

import java.util.LinkedList;
import java.util.List;

import io.rong.imkit.RongIM;
import okhttp3.Call;
import okhttp3.Response;
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
        mListTaskQueue = new LinkedList<>();
    }

    public static MyThreadPool executer() {
        if (null == mThreadPool) {
            mThreadPool = MyThreadPool.getInstance();
        }
        return mThreadPool;
    }

    public static List<Activity> getListTaskQueue() {
        if (mListTaskQueue == null) {
            mListTaskQueue = new LinkedList<>();
        }
        return mListTaskQueue;
    }

    /**
     * 获取一个布局填充器
     *
     * @return
     */
    public static LayoutInflater getInflater() {
        if (inflater == null) {
            inflater = (LayoutInflater) getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return inflater;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        initSDK();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initSDK() {
        BaseContext.initialize(this);
        Fresco.initialize(this);
        initIMKit();
        initNetWork();
    }

    private void initNetWork() {
        OkHttp.init(new OkHttp.IOkInit() {
            //原始响应
            //要在此处判断响应是否成功
            @Override
            public int judgeResultWhenFirstReceivedResponse(Call call, Response response, JSONObject json) {
                return json.getInteger("result");
            }

            /**
             * 网络请求出错
             * @param call
             * @param isCanceled  是否主动取消
             */
            @Override
            public void networkError(Call call, boolean isCanceled) {

            }

            //获取网络错误码
            @Override
            public void receivedNetworkErrorCode(Call call, Response response) {
                int code = response.code();
            }

            //分析服务端响应成功
            @Override
            public boolean resultSuccessByJudge(Call call, Response response, JSONObject json, int resultCode) {
                return false;
            }

            //分析服务端响应失败
            @Override
            public void judgeResultParseResponseFailed(Call call, Response response) {

            }

            //设置一些公共的默认的请求参数
            @Override
            public Param setDefaultParams(Param defaultParams) {
                return new Param();
            }

            //设置一些公共的默认请求头
            @Override
            public Header setDefaultHeader(Header defaultHeader) {
                return new Header();
            }
        });
    }

    private void initIMKit() {
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
        }
    }

    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public static Activity getTargetActivity(Class<? extends Activity> target) {
        Activity targetActivity = null;
        for (int i = 0; i < getListTaskQueue().size(); i++) {
            if (getListTaskQueue().get(i).getClass().equals(target)) {
                targetActivity = getListTaskQueue().get(i);
            }
        }
        if (targetActivity == null) {
            try {
                targetActivity = target.newInstance();
            } catch (InstantiationException e) {
                Log.d("App_getTargetAct", e.getMessage());
            } catch (IllegalAccessException e) {
                Log.d("App_getTargetAct", e.getMessage());
            } finally {
                return targetActivity;
            }
        }
        return targetActivity;
    }

    public static MyApplication getInstance() {
        if (myApplication == null) {
            myApplication = new MyApplication();
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
