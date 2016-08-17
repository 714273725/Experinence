package com.jack.myexperience.ui.adpater;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jack.myexperience.R;
import com.jack.myexperience.bean.Friend;
import com.jack.myexperience.statics.Statices;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/8/17.
 */
public class FriendListAdapter extends BaseRecyclerViewAdapter<Friend, FriendListAdapter.FriendHolder> {
    public FriendListAdapter(Context context) {
        this.mContext = context;
    }

    Context mContext;

    @Override
    public FriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new FriendHolder(view);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onBindViewHolder(FriendHolder holder, int position) {
        holder.mName.setText("小肥肥");
        holder.mHead.setImageURI(Uri.parse("http://b378.photo.store.qq.com/psb?/V14ZLUPZ3s50tL/Y5qIrjclDZCg8NWCtqPLz4ZuEIQHO0U2JtSj2lLSRtE!/m/dIYWXOHZOQAA&bo=wAOAAsADgAIFByQ!&rf=photolist"));
        holder.itemView.setOnClickListener(view -> forward());
    }

    private void forward() {
        if (RongIM.getInstance() != null)
            RongIM.getInstance().startPrivateChat(mContext,Statices.GEGE_ID.equals(Statices.CURRENT_ID) ? Statices.GEGE_ID : Statices.BAOBAO_ID, "小肥肥");
    }

    public static class FriendHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.friend_head)
        SimpleDraweeView mHead;
        @BindView(R.id.friend_name)
        TextView mName;

        public FriendHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
