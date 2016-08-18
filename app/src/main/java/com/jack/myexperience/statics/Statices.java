package com.jack.myexperience.statics;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/8/13.
 */
public class Statices {
    // UC的U3、百度的T5和腾讯的X5内核webview的调试工具---DebugGap


    public final static String TEST_IMAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "www" + File.separator + "test.jpg";
    public final static String TEST_LUBAN_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "www" + File.separator + "luban.jpg";
    public final static String TEST_ZELORY_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "www" + File.separator + "zelory.jpg";

    public final static String IM_KEY = "uwd1c0sxdd211";
    public final static String GEGE_TOKEN = "RrR92dUP1uO4YCo5sGhyxl03EsFSU9K/7Mihb7PujVb7XnFTC+f2Mm+cqgctrQwlugWcWtJa+ZZprS8CMPHSSzRW0/QyfATZ";
    public final static String BAOBAO_TOKEN = "MiGhRpbJEdxpJBcsFcIzGoKgStgtia1eBp2FieEEH/pV+0C0Ym9E0dRFyUgMEGp6PAtwFsDOibF4YNFy3T5IrZez7+Hsw1eU";
    public final static String BAOBAO_ID = "1693112041";
    public final static String GEGE_ID = "714276725";
    public static String CURRENT_ID = "714276725";


    private static final String HOST = "http://1547540x6k.iok.la/wed/";
    public static final String REGISTER = HOST + "app/user/register";
    public static final String LOGIN = HOST + "user/login";

    public static void setCurrentId(String id) {
        CURRENT_ID = id;
    }
}
