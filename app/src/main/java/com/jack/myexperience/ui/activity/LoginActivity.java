package com.jack.myexperience.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.jack.myexperience.MyApplication;
import com.jack.myexperience.R;
import com.jack.myexperience.base.BaseActivity;
import com.jack.myexperience.bean.User;
import com.jack.myexperience.statics.Statices;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ye.jian.ge.utils.ToastUtils;

/**
 * Created by gege on 2016/8/13.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_user_name)
    EditText mUserName;
    @BindView(R.id.et_user_password)
    EditText mUserPassword;
    @BindView(R.id.btn_login)
    Button mLogin;

    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        mLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void loginIM(String token) {
        if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                   /* Log.d("LoginActivity", "--onError" + errorCode);*/
                }
            });
        }
    }

    private void login() {
        showProgressDialog("登陆中。");
        final String name = mUserName.getText().toString().trim();
        final String password = mUserPassword.getText().toString().trim();
        if (name.isEmpty()) {
            ToastUtils.showToast("用户名不能为空");
            return;
        }
        if (password.isEmpty()) {
            ToastUtils.showToast("密码不能为空");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("user_name", name)
                        .add("user_password", password)
                        .build();
                Request request = new Request.Builder()
                        .url(Statices.LOGIN)
                        .post(formBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        dismissProgressDialog();
                        int result = JSON.parseObject(response.body().string()).getInteger("result");
                        switch (result) {
                            case 1:
                                User user = JSON.parseObject(response.body().string(), User.class);
                                loginIM(user.getUser_token());
                                forward(MainActivity.class);
                                finish();
                                break;
                            default:
                                break;
                        }
                    } else {
                        dismissProgressDialog();
                    }

                } catch (IOException e) {
                    dismissProgressDialog();
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
