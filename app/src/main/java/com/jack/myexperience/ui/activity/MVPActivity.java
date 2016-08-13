package com.jack.myexperience.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jack.myexperience.MyApplication;
import com.jack.myexperience.R;
import com.jack.myexperience.presenter.CityListPresenter;
import com.jack.myexperience.ui.adpater.CityListAdapter;
import com.jack.myexperience.ui.adpater.ProvinceListAdapter;
import com.jack.myexperience.viewinterface.CityListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/24 0024.
 */
public class MVPActivity extends Activity implements CityListView ,View.OnClickListener{
    CityListPresenter mPresenter;
    @Bind(R.id.rv_province)
    RecyclerView mShowList;
    @Bind(R.id.btn_get_province_list)
    Button mGetList;
    ProvinceListAdapter mProvinceListAdapter;
    CityListAdapter mCityListAdapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        ButterKnife.bind(this);
        MyApplication.getListTaskQueue().add(this);
        init();
    }

    private void init() {
        mGetList.setOnClickListener(this);
        mPresenter=new CityListPresenter(this);
    }

    @Override
    public void setCityByProvince(List list) {

    }

    @Override
    public void setProvinces(List list) {
        if(null==mProvinceListAdapter){
            mProvinceListAdapter=new ProvinceListAdapter();
            mProvinceListAdapter.setListener(this);
        }
        mShowList.setAdapter(mProvinceListAdapter);
        mProvinceListAdapter.setData(list);
    }

    @Override
    public void setCityForProvinces(List list) {
        if(null==mCityListAdapter){
            mCityListAdapter=new CityListAdapter();
        }
        mShowList.setAdapter(mCityListAdapter);
        mCityListAdapter.setData(list);
    }

    @Override
    protected void onDestroy() {
        MyApplication.getListTaskQueue().remove(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_get_province_list){
            mPresenter.getAll();
            initRecyclerView();
        }else if((int)v.getTag(R.id.first)==-1){
            int position= (int)v.getTag(R.id.second);
            mPresenter.getByPosition(position);
        }
    }

    private void initRecyclerView() {
        mProvinceListAdapter =new ProvinceListAdapter();
        mCityListAdapter=new CityListAdapter();
        mProvinceListAdapter.setListener(this);
        layoutManager=new LinearLayoutManager(this);
        mShowList.setLayoutManager(layoutManager);
    }
}
