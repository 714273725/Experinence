package com.jack.myexperience.statics;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/8/13.
 */
public class Statices {
    public final static String TEST_IMAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "www" + File.separator + "test.jpg";
    public final static String TEST_LUBAN_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "www" + File.separator + "luban.jpg";
    public final static String TEST_ZELORY_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "www" + File.separator + "zelory.jpg";

}
