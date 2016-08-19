package com.jack.myexperience.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.jack.myexperience.base.BaseActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by Administrator on 2016/8/17.
 */
public class ProxyActivity extends BaseActivity {
    private DexClassLoader loader;
    private Activity activity;
    private Object mBean;
    private Class clazz = null;
    private Class bean = null;

    @Override
    public void initCreate(Bundle savedInstanceState) {
        trends(savedInstanceState);
    }

    private void trends(Bundle savedInstanceState) {
        try {
            File file = getAssetFile(this, "trends.apk", "apk" + File.separator + "trends.apk");
            File dexOutputDir = this.getDir("dex", 0);
            loader = new DexClassLoader(file.getAbsolutePath(), dexOutputDir.getAbsolutePath(), null, getClassLoader());
            PackageInfo plocalObject = getPackageManager().getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
            if ((plocalObject.activities != null) && (plocalObject.activities.length > 0)) {
                String activityname = plocalObject.activities[0].name;
                clazz = loader.loadClass(activityname);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Method setProxy = clazz.getDeclaredMethod("setProxy", Activity.class);
            setProxy.setAccessible(true);
            activity = (Activity) clazz.newInstance();
            setProxy.invoke(activity, this);
            Method onCreate = clazz.getDeclaredMethod("onCreate", Bundle.class);
            onCreate.setAccessible(true);
            onCreate.invoke(activity, savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        // 调用插件activity的onStart方法
        Method onStart = null;
        try {
            onStart = clazz.getDeclaredMethod("onStart");
            onStart.setAccessible(true);
            onStart.invoke(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
// 调用插件activity的onDestroy方法
        Method onDestroy = null;
        try {
            onDestroy = clazz.getDeclaredMethod("onDestroy");
            onDestroy.setAccessible(true);
            onDestroy.invoke(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Method onDestroy = null;
        try {
            onDestroy = clazz.getDeclaredMethod("onPause");
            onDestroy.setAccessible(true);
            onDestroy.invoke(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Method onDestroy = null;
        try {
            onDestroy = clazz.getDeclaredMethod("onResume");
            onDestroy.setAccessible(true);
            onDestroy.invoke(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File getAssetFile(Context context, String filename, String soucre)
            throws IOException {
        int BUFFER_SIZE = 1024;
        byte[] buf = new byte[BUFFER_SIZE];
        int size;
        File file = new File(context.getCacheDir() + File.separator + filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        InputStream is = context.getAssets().open(soucre);
        BufferedInputStream bs = new BufferedInputStream(is);
        FileOutputStream fs = new FileOutputStream(file);
        while ((size = bs.read(buf)) != -1) {
            fs.write(buf, 0, size);
        }
        fs.flush();
        fs.close();
        bs.close();
        return file;
    }
}
