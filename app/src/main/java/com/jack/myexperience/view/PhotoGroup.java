package com.jack.myexperience.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jack.myexperience.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/16.
 */
public class PhotoGroup extends RelativeLayout {
    public PhotoGroup(Activity context) {
        super(context);
        this.setBackgroundColor(Color.BLACK);
        mAdapter = new PhotoAdapter();
        initSelf(context);
    }

    private void initSelf(Activity context) {
        this.mActivity = context;
    }

    private void init(Context context) {
        mCurrentNum = new TextView(context);
        mTotalNum = new TextView(context);
        mCurrentNum.setText("1");
        mTotalNum.setText("0");
        TextView split = new TextView(context);
        split.setText("/");
        RelativeLayout.LayoutParams bp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        split.setId(R.id.split);
        bp.addRule(CENTER_IN_PARENT);
        bp.addRule(ALIGN_PARENT_BOTTOM);
        split.setLayoutParams(bp);
        RelativeLayout.LayoutParams current = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams total = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        current.addRule(RelativeLayout.LEFT_OF, R.id.split);
        total.addRule(RelativeLayout.RIGHT_OF, R.id.split);
        current.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.split);
        total.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.split);
        mCurrentNum.setLayoutParams(current);
        mTotalNum.setLayoutParams(total);
        this.addView(split);
        this.addView(mCurrentNum);
        this.addView(mTotalNum);
        animatiom = AnimationUtils.loadAnimation(context, R.anim.scale);
    }

    public PhotoGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void start(List<Uri> photos) {
        this.removeAllViews();
        mPager = new ViewPager(mActivity);
        this.addView(mPager);
        init(this.getContext());
        this.setOnClickListener(dismiss);
        mPager.setAdapter(mAdapter);
        mAdapter.setPhotos(photos);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentNum.setText(String.valueOf(position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTotalNum.setText(String.valueOf(photos.size()));
        mActivity.getWindow().addContentView(this, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mPager.setAnimation(animatiom);
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    OnClickListener dismiss = view -> PhotoGroup.this.end();

    public boolean end() {
        if (mCurrentNum == null) {
            return false;
        } else {
            WindowManager.LayoutParams attr = mActivity.getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mActivity.getWindow().setAttributes(attr);
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            narrow = AnimationUtils.loadAnimation(mActivity, R.anim.narrow);
            this.setAnimation(narrow);
            this.removeAllViews();
            ((ViewGroup)this.getParent()).removeView(this);
            mCurrentNum = null;
            return true;
        }

        /*narrow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                this.removeAllViews();
                ((ViewGroup) layout.getParent()).removeView(layout);
                layout = null;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/
    }

    Activity mActivity;
    TextView mCurrentNum;
    TextView mTotalNum;
    ViewPager mPager;
    List<Uri> mPhotos;
    Animation animatiom;
    Animation narrow;
    PhotoAdapter mAdapter;


    public class PhotoAdapter extends PagerAdapter {
        public void setPhotos(List<Uri> photos) {
            this.photos = photos;
            notifyDataSetChanged();
        }

        List<Uri> photos;

        @Override
        public int getCount() {
            return photos == null ? 0 : photos.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            SimpleDraweeView view = new SimpleDraweeView(container.getContext());
            view.setImageURI(Uri.parse("http://img4.imgtn.bdimg.com/it/u=42046925,1559346389&fm=21&gp=0.jpg"));
            view.setOnClickListener(dismiss);
            container.addView(view);
            return view;
        }
    }


}
