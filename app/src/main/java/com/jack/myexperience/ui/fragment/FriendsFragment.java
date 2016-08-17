package com.jack.myexperience.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jack.myexperience.R;
import com.jack.myexperience.tools.BGARefreshLayoutBuilder;
import com.jack.myexperience.ui.adpater.FriendListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2016/8/17.
 */
public class FriendsFragment extends Fragment {
    @BindView(R.id.refresher)
    BGARefreshLayout mBGA;
    @BindView(R.id.friend_view)
    RecyclerView mFriendView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_fragment, container, false);
        ButterKnife.bind(this, view);
        BGARefreshLayoutBuilder.init(getContext(), mBGA, false);
        mBGA.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                mBGA.endRefreshing();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                return false;
            }
        });
        mFriendView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mFriendView.setAdapter(new FriendListAdapter(getActivity()));
        return view;
    }

}
