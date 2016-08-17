package com.jack.myexperience.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jack.myexperience.R;

/**
 * Created by Administrator on 2016/8/15.
 */
public class MultiplexDialog extends Dialog {
    ImageView mImage;
    TextView tipTextView;
    Animation hyperspaceJumpAnimation;

    public MultiplexDialog(Context context) {
        super(context, R.style.loadingdialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.costom_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        mImage = (ImageView) v.findViewById(R.id.img);
        tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_bg);
        this.setCancelable(false);// 不可以用“返回键”取消
        this.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void dismissProgressDialog() {
        this.dismiss();
    }

    public void showProgressDialog(String msg) {
        if (this.isShowing()) {
            this.dismissProgressDialog();
        }
        tipTextView.setText(msg);
        mImage.startAnimation(hyperspaceJumpAnimation);
        this.show();
    }

    public void setText(final String msg) {
        if (tipTextView != null) {
            tipTextView.post(new Runnable() {
                @Override
                public void run() {
                    tipTextView.setText(msg);
                }
            });
        }
    }
}
