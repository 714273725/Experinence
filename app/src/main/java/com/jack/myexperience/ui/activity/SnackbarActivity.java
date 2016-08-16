package com.jack.myexperience.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.widget.Button;

import com.jack.myexperience.R;
import com.jack.myexperience.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/15.
 */
public class SnackbarActivity extends BaseActivity{
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.cl)
    CoordinatorLayout mCL;
    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.snackbar_activity);
        ButterKnife.bind(this);
        mBtn.setOnClickListener( view -> Snackbar.make(mCL,"点我",Snackbar.LENGTH_LONG).show());
    }
}
