package com.jack.myexperience.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.jack.myexperience.R;
import com.jack.myexperience.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gege on 2016/8/13.
 */
public class WelComeActivity extends BaseActivity {
    @BindView(R.id.iv_welcome)
    ImageView mWelcome;

    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        mWelcome.postDelayed(()->{forwardLogin();}, 1000);
    }
    public void forwardLogin(){
        forward(LoginActivity.class);
        finish();
    }
}
