package com.jack.myexperience.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jack.myexperience.R;
import com.jack.myexperience.base.BaseActivity;
import com.jack.myexperience.view.PhotoGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ye.jian.ge.utils.LogUtils;
import ye.jian.ge.utils.ToastUtils;

/**
 * Created by Administrator on 2016/8/16.
 */
public class AnimationActivity extends BaseActivity {
    @BindView(R.id.btn_animation)
    Button mBtnAnimation;
    ViewGroup layout;
    PhotoAdapter mAdapter;
    List<Uri> photos = new ArrayList<>();
    Animation animatiom;
    Animation narrow;
        PhotoGroup group;
    {
        photos.add(null);
        photos.add(null);
        photos.add(null);
    }

    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.animation_activity);
        ButterKnife.bind(this);
        mBtnAnimation.setOnClickListener(view -> start());
        mAdapter = new PhotoAdapter();

    }

    private void start() {
        group = new PhotoGroup(this);
        group.start(photos);
        /*animatiom = AnimationUtils.loadAnimation(this, R.anim.scale);
        layout = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.animation_layout, null);
        ViewPager pager = (ViewPager) layout.findViewById(R.id.pager);
        layout.setOnClickListener(view -> initAnimation());
        pager.setAdapter(mAdapter);
        mAdapter.setPath(photos);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ToastUtils.showToast(position + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        getWindow().addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        pager.setAnimation(animatiom);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
    }

    private void initAnimation() {
        WindowManager.LayoutParams attr = getWindow().getAttributes();
        attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attr);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        narrow = AnimationUtils.loadAnimation(this, R.anim.narrow);
        layout.setAnimation(narrow);
        narrow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout.removeAllViews();
                ((ViewGroup) layout.getParent()).removeView(layout);
                layout = null;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    View.OnClickListener onPhotoClick = view -> initAnimation();

    public class PhotoAdapter extends PagerAdapter {
        public void setPath(List<Integer> path) {
            this.mPath = path;
            notifyDataSetChanged();
        }

        List<Integer> mPath;

        @Override
        public int getCount() {
            return mPath == null ? 0 : mPath.size();
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
            //http://img4.imgtn.bdimg.com/it/u=42046925,1559346389&fm=21&gp=0.jpg
            view.setImageURI(Uri.parse("http://img4.imgtn.bdimg.com/it/u=42046925,1559346389&fm=21&gp=0.jpg"));
            view.setOnClickListener(view1 -> initAnimation());
            container.addView(view);
            return view;
        }
    }

    @Override
    public void onBackPressed() {
        if(group!=null){
            if (group.end()) {
                return;
            }
        }
        super.onBackPressed();
    }
}
