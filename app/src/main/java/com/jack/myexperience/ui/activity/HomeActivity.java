package com.jack.myexperience.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.jack.myexperience.R;
import com.jack.myexperience.base.BaseActivity;
import com.zhy.view.CircleMenuLayout;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import dalvik.system.DexClassLoader;
import ye.jian.ge.annotation.SaveStatue;

/**
 * Created by Administrator on 2016/8/17.
 */
public class HomeActivity extends BaseActivity {
    @SaveStatue
    int[] items = {R.mipmap.icon_8, R.mipmap.icon_24, R.mipmap.icon_27, R.mipmap.icon_33, R.mipmap.icon_22, R.mipmap.icon_21, R.mipmap.icon_16, R.mipmap.icon_40};
    @SaveStatue
    String[] texts = {"0", "1", "2", "3", "4", "5", "6", "7"};
    @BindView(R.id.circle_menu)
    CircleMenuLayout mCirclMenu;

    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);
        measure();
    }

    private void measure() {
        mCirclMenu.setMenuItemIconsAndTexts(items, texts)
                .setGravityWithMargin(CircleMenuLayout.GRAVITY_BOTTOM_CENTER, 30);
        mCirclMenu.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                switch (pos) {
                    case 1:
                        forward(MainActivity.class);
                        break;
                    case 2:
                        forward(ProxyActivity.class);
                        break;
                    case 3:
                        break;
                }
            }

            @Override
            public void itemCenterClick(View view) {

            }
        });
    }


}
