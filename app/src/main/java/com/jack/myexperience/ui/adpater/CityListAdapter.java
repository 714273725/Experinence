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
public class CityListAdapter extends BaseRecyclerViewAdapter<CityBean.Province.City> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView= MyApplication.getInflater().inflate(R.layout.item_get_province_adapter,parent,false);
        CityViewHolder holder=new CityViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CityViewHolder)holder).mCityName.setText(getTargetPositionData(position).getCityname());
    }
    public static class CityViewHolder extends RecyclerView.ViewHolder{
        public TextView mCityName;
        public CityViewHolder(View itemView) {
            super(itemView);
            mCityName= (TextView) itemView.findViewById(R.id.tv_item_get_province_adapter);
        }
    }
}
