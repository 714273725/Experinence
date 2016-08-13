package ye.jian.ge.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import ye.jian.ge.BaseContext;
import ye.jian.ge.R;
import ye.jian.ge.adapter.YunLabelAdapter;
import ye.jian.ge.utils.LogUtils;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class WeightYunLayout extends LinearLayout {
    public static int DEFAULT_COUNT_A_LINE = 4;
    public static int DEFAULT_ITEM_MARGIN_RIGHT = 0;
    public static int DEFAULT_ITEM_MARGIN_LEFT = 0;
    public static int DEFAULT_ITEM_MARGIN_TOP = 0;
    public static int DEFAULT_ITEM_MARGIN_BOTTOM = 0;

    AdapterDataSetObserver mObserver;

    public void setLineItemCounts(int lineItemCounts) {
        this.mLineItemCounts = lineItemCounts;
        //reloadData();
    }

    public int mLineItemCounts = DEFAULT_COUNT_A_LINE;
    public int mItemMarginRight;
    public int mItemMarginLeft;
    public int mItemMarginTop;
    public int mItemMarginBottom;
    public boolean mIgnoreHeadMargin = false;
    public boolean mIgnoreEndMargin = false;

    public WeightYunLayout(Context context) {
        super(context);
        init(context, null);
    }

    List<LinearLayout> mChildGroupArray;

    {
        mChildGroupArray = new ArrayList<>();
    }

    public void setAdapter(YunLabelAdapter adapter) {
        this.mAdapter = adapter;
        if (null != mAdapter && null != mObserver) {
            mAdapter.unregisterDataSetObserver(mObserver);
        }
        removeAllViews();
        mObserver = new AdapterDataSetObserver();
        if (null != mAdapter && null != mObserver) {
            mAdapter.registerDataSetObserver(mObserver);
        }
    }

    YunLabelAdapter mAdapter;

    public WeightYunLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WeightYunLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (null == attrs) {
            mLineItemCounts = DEFAULT_COUNT_A_LINE;
            mItemMarginRight = DEFAULT_ITEM_MARGIN_RIGHT;
            mItemMarginLeft = DEFAULT_ITEM_MARGIN_LEFT;
            mItemMarginTop = DEFAULT_ITEM_MARGIN_TOP;
            mItemMarginBottom = DEFAULT_ITEM_MARGIN_BOTTOM;
            mIgnoreHeadMargin = false;
            mIgnoreEndMargin = false;
        } else {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.WeightYunLayout, 0, 0);
            mLineItemCounts = array.getInt(R.styleable.WeightYunLayout_line_counts, DEFAULT_COUNT_A_LINE);
            mItemMarginRight = array.getInteger(R.styleable.WeightYunLayout_item_marginRight, DEFAULT_ITEM_MARGIN_RIGHT);
            mItemMarginLeft = array.getInteger(R.styleable.WeightYunLayout_item_marginLeft, DEFAULT_ITEM_MARGIN_LEFT);
            mItemMarginTop = array.getInt(R.styleable.WeightYunLayout_item_marginTop, DEFAULT_ITEM_MARGIN_TOP);
            mItemMarginBottom = array.getInteger(R.styleable.WeightYunLayout_item_marginBottom, DEFAULT_ITEM_MARGIN_BOTTOM);
            mIgnoreHeadMargin = array.getBoolean(R.styleable.WeightYunLayout_item_ignore_head_margin, false);
            mIgnoreEndMargin = array.getBoolean(R.styleable.WeightYunLayout_item_ignore_end_margin, false);
        }
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            WeightYunLayout.this.post(new Runnable() {
                @Override
                public void run() {
                    reloadData();
                }
            });
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }

    private void reloadData() {
        if (null != mChildGroupArray) {
            mChildGroupArray.clear();
        } else {
            mChildGroupArray = new ArrayList<>();
        }
        WeightYunLayout.this.removeAllViews();
        //遍历有几个item,然后根据每行有几个item计算需要几个LinearLayout
        //并把这几个LinearLayout添加到父布局
        for (int i = 0; i < mAdapter.getCount(); i++) {
            if (i % mLineItemCounts == 0) {
                LinearLayout layout = new LinearLayout(BaseContext.getInstance());
                layout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                mChildGroupArray.add(layout);
                WeightYunLayout.this.addView(layout);
            }
        }
        LogUtils.e(WeightYunLayout.class, "how many Linear:" + mChildGroupArray.size());
        if (null == mAdapter) {
            throw new NullPointerException("WeightYunLayout 没有设置适配器");
        }
        //遍历每个线性布局
        for (int i = 0; i < mChildGroupArray.size(); i++) {
            //遍历adapter中每个View，判断view是属于哪一个线性布局的，然后添加到对应的线性布局中
            for (int j = 0; j < mAdapter.getCount(); j++) {
                if (j / mLineItemCounts == i) {
                    View view = mAdapter.getView(j, null, mChildGroupArray.get(i));
                    LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    //判断是不是每一行的头尾，如果不是则设置margin，如果是头和尾则根据条件判断是否需要设置margin
                    if (j % mLineItemCounts != 0 && j % mLineItemCounts != mLineItemCounts - 1) {
                        params.setMargins(mItemMarginLeft, mItemMarginTop, mItemMarginRight, mItemMarginBottom);
                    } else {
                        if (j % mLineItemCounts == 0 && !mIgnoreHeadMargin)  //如果不忽略头的margin,则设置margin
                            params.setMargins(mItemMarginLeft, mItemMarginTop, mItemMarginRight, mItemMarginBottom);
                        if (j % mLineItemCounts == mLineItemCounts - 1 && !mIgnoreEndMargin) //如果不忽略尾的margin,则设置margin
                            params.setMargins(mItemMarginLeft, mItemMarginTop, mItemMarginRight, mItemMarginBottom);
                    }
                    view.setLayoutParams(params);
                    //将子View加到对应的线性布局中去
                    mChildGroupArray.get(i).addView(view);
                }
            }
        }
        //如果item的数目不是刚好整行的，则判断需要在最后一个线性布局中添加几个填充的View
        if (mAdapter.getCount() % mLineItemCounts != 0) {
            int temp = mLineItemCounts - mAdapter.getCount() % mLineItemCounts;
            for (int l = 0; l < temp; l++) {
                TextView textView = new TextView(BaseContext.getInstance());
                LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                params.setMargins(mItemMarginLeft, mItemMarginTop, mItemMarginRight, mItemMarginBottom);
                textView.setLayoutParams(params);
                mChildGroupArray.get(mChildGroupArray.size() - 1).addView(textView);
            }
        }
        invalidate();
    }
}
