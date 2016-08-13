package com.jack.myexperience.ui.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jack.myexperience.MyApplication;
import com.jack.myexperience.R;
import com.jack.myexperience.bean.CityBean;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
public class ProvinceListAdapter extends BaseRecyclerViewAdapter<CityBean.Province> {
    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }
    View.OnClickListener listener;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView= MyApplication.getInflater().inflate(R.layout.item_get_province_adapter,parent,false);
        GetProvinceViewHolder holder=new GetProvinceViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(R.id.first,-1);
        holder.itemView.setTag(R.id.second,position);
        ((GetProvinceViewHolder)holder).mProvinceName.setText(getTargetPositionData(position).getProvince());
        if(listener!=null){
            holder.itemView.setOnClickListener(listener);
        }
    }
    public static class GetProvinceViewHolder extends RecyclerView.ViewHolder{
        public TextView mProvinceName;
        public GetProvinceViewHolder(View itemView) {
            super(itemView);
            mProvinceName= (TextView) itemView.findViewById(R.id.tv_item_get_province_adapter);
        }
    }
}
