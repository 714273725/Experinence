package com.jack.myexperience.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jack.myexperience.R;
import com.jack.myexperience.base.BaseActivity;
import com.jack.myexperience.ui.fragment.FriendsFragment;
import com.jack.myexperience.ui.manager.PageManager;
import com.zhy.view.CircleMenuLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.fragment.ConversationListFragment;
import ye.jian.ge.annotation.SaveStatue;

/**
 * Created by Administrator on 2016/8/17.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {
    @SaveStatue
    int[] items = {R.mipmap.icon_8, R.mipmap.icon_24, R.mipmap.icon_27, R.mipmap.icon_33, R.mipmap.icon_22, R.mipmap.icon_21, R.mipmap.icon_16, R.mipmap.icon_40};
    @SaveStatue
    String[] texts = {"0", "1", "2", "3", "4", "5", "6", "7"};
    @BindView(R.id.circle_menu)
    CircleMenuLayout mCirclMenu;
    @BindView(R.id.conversation_icon)
    ImageView mConversation;
    @BindView(R.id.friends_icon)
    ImageView mFriends;
    PageManager mPagerManger;

    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);
        measure();
        initViews();
        initListener();
    }

    private void initViews() {
        mPagerManger = new PageManager(R.id.container, getSupportFragmentManager());
        if (mPagerManger != null)
            mPagerManger.add(ConversationListFragment.class, PageManager.CONVERSION, null);
        mConversation.setSelected(true);
    }

    private void initListener() {
        mConversation.setOnClickListener(this);
        mFriends.setOnClickListener(this);
    }

    private void measure() {
        mCirclMenu.setMenuItemIconsAndTexts(items, texts)
                .setGravityWithMargin(CircleMenuLayout.GRAVITY_BOTTOM_CENTER, 20);
        mCirclMenu.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                switch (pos) {
                    case 1:
                        forward(MainActivity.class);
                        break;
                    case 2:
                        forward(ProxyActivity.class);
                        break;
                    case 3:
                        break;
                }
            }

            @Override
            public void itemCenterClick(View view) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.conversation_icon:
                clearSelection();
                mConversation.setSelected(true);
                if (mPagerManger != null)
                    mPagerManger.add(ConversationListFragment.class, PageManager.CONVERSION, null);
                break;
            case R.id.friends_icon:
                clearSelection();
                if (mPagerManger != null)
                    mPagerManger.add(FriendsFragment.class, PageManager.FRIENDS, null);
                mFriends.setSelected(true);
                break;
        }
    }

    private void clearSelection() {
        mConversation.setSelected(false);
        mFriends.setSelected(false);
    }
}
