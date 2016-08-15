package com.jack.myexperience.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.jack.myexperience.R;
import com.jack.myexperience.base.BaseActivity;
import com.zhy.view.CircleMenuLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/13.
 */
public class MenuActivity extends BaseActivity {
    int[] items = {R.mipmap.icon_8, R.mipmap.icon_24, R.mipmap.icon_27, R.mipmap.icon_33, R.mipmap.icon_22, R.mipmap.icon_21, R.mipmap.icon_16, R.mipmap.icon_40};
    String[] texts = {"0", "1", "2", "3", "4", "5", "6", "7"};
    @BindView(R.id.circle_menu)
    CircleMenuLayout mCirclMenu;

    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        mCirclMenu.setMenuItemIconsAndTexts(items, texts)
                .setGravity(CircleMenuLayout.GRAVITY_BOTTOM_LEFT);
        mCirclMenu.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                switch (pos) {
                    case 1:
                        mCirclMenu.setGravity(CircleMenuLayout.GRAVITY_BOTTOM_CENTER);
                        break;
                    case 2:
                        mCirclMenu.setGravity(CircleMenuLayout.GRAVITY_BOTTOM_RIGHT);
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
