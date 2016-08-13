package com.jack.myexperience.ui.activity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jack.myexperience.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 动态设置activity背景透明度
 * 使用透明主题，添加一层不透明的缓冲层
 * Created by Administrator on 2016/2/23 0023.
 */
public class TransparentActivity extends Activity implements SeekBar.OnSeekBarChangeListener,View.OnClickListener{
    @Bind(R.id.rl_root_background)
    RelativeLayout mRootBackGround;
    @Bind(R.id.rl_transparent_background)
    RelativeLayout mBufferBackGround;
    @Bind(R.id.tv_root_progressbar)
    TextView mRootProgress;
    @Bind(R.id.tv_buffer_progressbar)
    TextView mBufferProgress;
    @Bind(R.id.tv_drawView_progressbar)
    TextView mDrawViewProgress;
    @Bind(R.id.sb_root)
    SeekBar mRoot;
    @Bind(R.id.sb_buffer)
    SeekBar mBuffer;
    @Bind(R.id.sb_drawView)
    SeekBar mDrawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_transparent);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mDrawView.setOnSeekBarChangeListener(this);
        mRoot.setOnSeekBarChangeListener(this);
        mBuffer.setOnSeekBarChangeListener(this);
        mRootBackGround.setOnClickListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float temp;
        switch (seekBar.getId()){
            //设置根布局透明度
            case R.id.sb_root:
                mRootProgress.setText(progress+"%");
                temp=(100-progress)/100.0f;
                mRootBackGround.setAlpha(temp);
                break;
            //设置缓冲层的透明度
            case R.id.sb_buffer:
                mBufferProgress.setText(progress+"%");
                temp=(100-progress)/100.0f;
                mBufferBackGround.setAlpha(temp);
                break;
            //设置getDecorView的透明度
            case R.id.sb_drawView:
                mDrawViewProgress.setText(progress+"%");
                temp=(100-progress)/100.0f;
                getWindow().getDecorView().setAlpha(temp);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_root_background:
                mRoot.setProgress(0);
                mBuffer.setProgress(0);
                mDrawView.setProgress(0);
                break;
            default:
                break;
        }
    }
}
