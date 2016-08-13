package com.jack.myexperience.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jack.myexperience.R;
import com.jack.myexperience.base.BaseActivity;
import com.jack.myexperience.statics.Statices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import ye.jian.ge.utils.LogUtils;

/**
 * Created by Administrator on 2016/8/13.
 */
public class ImageCompressActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.artwork)
    ImageView artwork;
    @BindView(R.id.compress_by_luban)
    Button compressByLuban;
    @BindView(R.id.luban)
    ImageView luban;
    @BindView(R.id.compress_by_zelory)
    Button compressByZelory;
    @BindView(R.id.zelory)
    ImageView zelory;

    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_image_compress);
        ButterKnife.bind(this);
        compressByLuban.setOnClickListener(this);
        compressByZelory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.compress_by_luban:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Luban.get(getThis()).load(new File(Statices.TEST_IMAGE_PATH)).putGear(Luban.THIRD_GEAR).setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                LogUtils.e("LuBan开始时间" + Calendar.getInstance().getTimeInMillis());
                            }

                            @Override
                            public void onSuccess(File file) {
                                LogUtils.e("LuBan结束时间" + Calendar.getInstance().getTimeInMillis());
                                File file1 = new File(Statices.TEST_LUBAN_PATH);
                                if (!file1.exists()) {
                                    try {
                                        file1.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                fileChannelCopy(file, file1);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }).launch();
                    }
                });
                break;
            case R.id.compress_by_zelory:
                LogUtils.e("zelory开始时间" + Calendar.getInstance().getTimeInMillis());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Compressor.getDefault(getThis())
                                .compressToFileAsObservable(new File(Statices.TEST_IMAGE_PATH))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<File>() {
                                    @Override
                                    public void call(File file) {
                                        LogUtils.e("zelory结束时间" + Calendar.getInstance().getTimeInMillis());
                                        File file1 = new File(Statices.TEST_ZELORY_PATH);
                                        if (!file1.exists()) {
                                            try {
                                                file1.createNewFile();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        fileChannelCopy(file, file1);
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                    }
                                });
                    }
                });
                break;
        }
    }


    public void fileChannelCopy(File s, File t) {

        FileInputStream fi = null;

        FileOutputStream fo = null;

        FileChannel in = null;

        FileChannel out = null;

        try {

            fi = new FileInputStream(s);

            fo = new FileOutputStream(t);

            in = fi.getChannel();//得到对应的文件通道

            out = fo.getChannel();//得到对应的文件通道

            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                fi.close();

                in.close();

                fo.close();

                out.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }
}
