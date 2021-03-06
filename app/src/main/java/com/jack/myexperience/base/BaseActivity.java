package com.jack.myexperience.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jack.myexperience.view.MultiplexDialog;

import ye.jian.ge.annotationHelper.StatueBinder;

/**
 * Created by gege on 2016/8/13.
 */
public abstract class BaseActivity extends AppCompatActivity {
    MultiplexDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCreate(savedInstanceState);
    }

    public abstract void initCreate(Bundle savedInstanceState);

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        StatueBinder.saveStatue(this, outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        StatueBinder.bindStatue(this, savedInstanceState);
    }

    public BaseActivity getThis() {
        return this;
    }

    public void forward(Class<? extends Activity> clz) {
        if (null != clz) {
            Intent intent = new Intent(this, clz);
            startActivity(intent);
        }
    }

    public void showProgressDialog(String msg) {
        if (mDialog == null) {
            mDialog = new MultiplexDialog(this);
        }
        mDialog.showProgressDialog(msg);
    }

    public void setProgressDialogText(String msg) {
        mDialog.setText(msg);
    }

    public void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.dismissProgressDialog();
        }
    }
}
