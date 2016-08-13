package com.jack.myexperience.ui.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jack.myexperience.MyApplication;
import com.jack.myexperience.R;

/**
 * Created by Administrator on 2016/2/23 0023.
 */
public class FunctionAdapter extends BaseRecyclerViewAdapter<String> {
    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    View.OnClickListener listener;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView=MyApplication.getInflater().inflate(R.layout.item_function_adapter,parent,false);
        FunctionViewHolder holder=new FunctionViewHolder(convertView);
        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        ((FunctionViewHolder)holder).mFunctionName.setText(getTargetPositionData(position));
        if(listener!=null){
            holder.itemView.setOnClickListener(listener);
        }
    }
    public static class FunctionViewHolder extends RecyclerView.ViewHolder{
        public TextView mFunctionName;
        public FunctionViewHolder(View itemView) {
            super(itemView);
            mFunctionName= (TextView) itemView.findViewById(R.id.tv_item_function_adapter);
        }
    }
}
