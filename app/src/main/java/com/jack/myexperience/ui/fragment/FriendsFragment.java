package com.jack.myexperience.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.jack.myexperience.R;
import com.jack.myexperience.bean.Friend;
import com.jack.myexperience.tools.BGARefreshLayoutBuilder;
import com.jack.myexperience.ui.adpater.FriendListAdapter;
import com.xycode.xylibrary.adapter.XAdapter;
import com.xycode.xylibrary.okHttp.Param;
import com.xycode.xylibrary.unit.ViewTypeUnit;
import com.xycode.xylibrary.xRefresher.XRefresher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2016/8/17.
 */
public class FriendsFragment extends Fragment {
    XAdapter<Friend> mAdapter;
    @BindView(R.id.xrefresher)
    XRefresher mFriendsView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_fragment, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new XAdapter<Friend>(getActivity(), new ArrayList<>()) {
            @Override
            public void bindingHolder(CustomHolder holder, List<Friend> dataList, int pos) {
                holder.setText(R.id.friend_name, "小小只")
                        .setText(R.id.friend_sign, "我是一只小小小小鸟")
                .setImageURI(R.id.friend_head, Uri.parse("http://b378.photo.store.qq.com/psb?/V14ZLUPZ3s50tL/Y5qIrjclDZCg8NWCtqPLz4ZuEIQHO0U2JtSj2lLSRtE!/m/dIYWXOHZOQAA&bo=wAOAAsADgAIFByQ!&rf=photolist"));
            }

            //ViewTypeUnit 包含layoutId
            @Override
            protected ViewTypeUnit getViewTypeUnitForLayout(Friend item) {
                return new ViewTypeUnit(item.getUser_name(), R.layout.friend_item);
            }
        };
        mFriendsView.setup(getActivity(), mAdapter, false, new XRefresher.OnSwipeListener() {
            @Override
            public void onRefresh() {

            }
        }, new XRefresher.RefreshRequest() {
            /**
             *
             * @param params 提供了基本参数，第几页 ，size
             * @return 接口路径
             */
            @Override
            public String setRequestParamsReturnUrl(Param params) {
                params.add("user_id", String.valueOf(1)).add("collection_type", String.valueOf(0));
                String url = "http://api.tbtj1688.com/app/user/resumeCollection";
                return url;
            }

            @Override
            public List setListData(JSONObject json) {
                int count = json.getInteger("pagerCount");
                ArrayList<Friend> friends = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Friend friend = new Friend();
                    friend.setUser_name("呵呵");
                    friends.add(friend);
                }
                return friends;
            }
        });
        mFriendsView.refreshList();
        return view;
    }

}
