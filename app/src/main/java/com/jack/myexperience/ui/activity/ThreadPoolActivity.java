package com.jack.myexperience.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.jack.myexperience.MyApplication;
import com.jack.myexperience.R;
import com.jack.myexperience.model.CancelableTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/26 0026.
 */
public class ThreadPoolActivity extends Activity implements View.OnClickListener{
    @Bind(R.id.one)
    SeekBar one;
    @Bind(R.id.two)
    SeekBar two;
    @Bind(R.id.three)
    SeekBar three;
    @Bind(R.id.four)
    SeekBar four;
    @Bind(R.id.five)
    SeekBar five;
    @Bind(R.id.six)
    SeekBar six;
    @Bind(R.id.seven)
    SeekBar seven;
    @Bind(R.id.eight)
    SeekBar eight;
    @Bind(R.id.nine)
    SeekBar nine;
    @Bind(R.id.btn_cached)
    Button mCached;
    @Bind(R.id.btn_fixed)
    Button mFixed;
    @Bind(R.id.btn_cancel_cached)
    Button mCancelCached;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mCached.setOnClickListener(this);
        mFixed.setOnClickListener(this);
        mCancelCached.setOnClickListener(this);
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        one.setProgress(msg.arg1);
                        break;
                    case 1:
                        two.setProgress(msg.arg1);
                        break;
                    case 2:
                        three.setProgress(msg.arg1);
                        break;
                    case 3:
                        four.setProgress(msg.arg1);
                        break;
                    case 4:
                        five.setProgress(msg.arg1);
                        break;
                    case 5:
                        six.setProgress(msg.arg1);
                        break;
                    case 6:
                        seven.setProgress(msg.arg1);
                        break;
                    case 7:
                        eight.setProgress(msg.arg1);
                        break;
                    case 8:
                        nine.setProgress(msg.arg1);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cached:
                cancelCached();
                runCached();
                break;
            case R.id.btn_fixed:
                runFixed();
                break;
            case R.id.btn_cancel_cached:
                cancelCached();
                break;
        }
    }

    private void cancelCached() {
        MyApplication.executer().cancelAllNow();
    }

    private void runFixed() {
        for(int i=3;i<9;i++){
            MyApplication.executer().executeWhenFree(new CancelableTask(mHandler,i));
        }
    }

    private void runCached() {
        for(int i=0;i<3;i++){
            MyApplication.executer().executeNow(new CancelableTask(mHandler,i));
        }
    }
}
