package com.jack.myexperience.bean;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/1/11 0011.
 */
public class HeaterBean {
    public void setName(String mName) {
        this.mName = mName;
    }

    public String getName() {
        return mName;
    }

    private String mName;
    @Inject
    public HeaterBean(String name) {
        this.mName=name;
    }
    public void on() {
        Log.e(mName, "开始烧开水啦");
    }
    public void off() {
        Log.e(mName, "关闭加热器");
    }
}
