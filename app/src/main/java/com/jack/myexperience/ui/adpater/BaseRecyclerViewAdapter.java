package com.jack.myexperience.ui.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/2/23 0023.
 */
public abstract class BaseRecyclerViewAdapter<T,K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {
    List data;
    public void setData(List data) {
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }
    public T getTargetPositionData(int position){
        if(position<getItemCount()){
            return (T)data.get(position);
        }else {
            return null;
        }
    }
}
