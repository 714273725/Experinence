package com.jack.myexperience.ui.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jack.myexperience.MyApplication;
import com.jack.myexperience.R;

/**
 * Created by Administrator on 2016/2/23 0023.
 */
public class FunctionAdapter extends BaseRecyclerViewAdapter<String,FunctionAdapter.FunctionViewHolder> {
    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public FunctionAdapter(Context context) {
        this.context = context;
    }

    Context context;
    static View.OnClickListener listener;

    @Override
    public FunctionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView= LayoutInflater.from(context).inflate(R.layout.item_function_adapter,parent,false);
        FunctionViewHolder holder=new FunctionViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(FunctionViewHolder holder, int position) {
        holder.mFunctionName.setText(getTargetPositionData(position));
        holder.mFunctionName.setTag(position);
        holder.itemView.setTag(position);
        if(listener!=null){
            holder.view.setOnClickListener(listener);
        }
    }
    public class FunctionViewHolder extends RecyclerView.ViewHolder{
        public TextView mFunctionName;
        public View view;
        public FunctionViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            mFunctionName = (TextView) itemView.findViewById(R.id.tv_item_function_adapter);
        }
    }
}
