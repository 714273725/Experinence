package com.jack.myexperience.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.jack.myexperience.R;
import com.jack.myexperience.bean.HeaterBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * Rx响应式编程入门
 * Created by Administrator on 2016/2/24 0024.
 */
public class RxAndroidActivity extends Activity {
    @BindView(R.id.btn_activity_rx)
    Button mBook;
    Observer<HeaterBean> mReader;
    List<HeaterBean> mList=new ArrayList<>();
    HeaterBean mHeater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android);
        ButterKnife.bind(this);
        init();
    }
    private void init() {
        for(int i=0;i<5;i++){
            mHeater=new HeaterBean("");
            mHeater.setName("加热器"+i);
            mList.add(mHeater);
        }
        mReader=new Observer<HeaterBean>() {
            @Override
            public void onCompleted() {
                mBook.post(new Runnable() {
                    @Override
                    public void run() {
                        mBook.setText("订阅");
                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final HeaterBean heaterBean) {
                try {
                    Thread.sleep(2000);
                    mBook.post(new Runnable() {
                        @Override
                        public void run() {
                            mBook.setText(heaterBean.getName());
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_activity_rx:
                v.post(new Runnable() {
                    @Override
                    public void run() {
                        Observable.from(mList).subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.newThread())  //mReader中的操作在子线程中执行
                                .subscribe(mReader);
                    }
                });
                break;
        }
    }
}
