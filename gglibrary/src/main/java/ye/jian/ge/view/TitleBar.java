package ye.jian.ge.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ye.jian.ge.R;
import ye.jian.ge.utils.ScreenUtils;

/**
 * 自定义标题栏，包含返回按钮以及页面标题
 * Created by Administrator on 2015/11/3.
 */
public class TitleBar extends RelativeLayout {
    private TextView tv_titlebar_back;
    private TextView tv_titlebar_title;
    private ImageView iv_titlebar_right;

    public TitleBar(final Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.custom_titile_bar, this);
        setImmerseLayout();

        tv_titlebar_back = (TextView) findViewById(R.id.tv_titlebar_back);
        tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
        iv_titlebar_right = (ImageView) findViewById(R.id.iv_titlebar_right);

        this.setBackgroundResource(R.color.title_background);

        //自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        String titleText = ta.getString(R.styleable.TitleBar_titleText);
        tv_titlebar_title.setText(titleText);

        int backVisibility = ta.getInt(R.styleable.TitleBar_backVisibilty, 0);
        int rightVisibility = ta.getInt(R.styleable.TitleBar_rightVisibilty, 0);
        tv_titlebar_back.setVisibility(backVisibility);
        setBackListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
        iv_titlebar_right.setVisibility(rightVisibility);

        int rightSrc = ta.getResourceId(R.styleable.TitleBar_rigthSrc, -1);
        if (rightSrc != -1) iv_titlebar_right.setImageResource(rightSrc);
    }

    /**
     * 沉浸模式,Android 4.4以上版本时，实现沉浸式状态栏，此时的TitleBar需要设定paddingTop=状态栏高度
     */
    protected void setImmerseLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = ScreenUtils.getStatusBarHeight(getContext());
            setPadding(0, statusBarHeight, 0, 0);
        }
    }

    /**
     * 设置返回按钮的点击监听
     */
    public void setBackListener(OnClickListener listener) {
        tv_titlebar_back.setOnClickListener(listener);
    }

    /**
     * 设置标题栏右边的按钮的点击监听
     */
    public void setRightListener(OnClickListener listener) {
        iv_titlebar_right.setOnClickListener(listener);
    }

    public void setRightButtonVisibility(int visibility) {
        switch (visibility) {
            case View.GONE:
                iv_titlebar_right.setVisibility(View.GONE);
                break;
            case View.VISIBLE:
                iv_titlebar_right.setVisibility(View.VISIBLE);
                break;
            case View.INVISIBLE:
                iv_titlebar_right.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * 设置页面标题
     */
    public void setTitleText(String text) {
        tv_titlebar_title.setText(text);
    }

}
