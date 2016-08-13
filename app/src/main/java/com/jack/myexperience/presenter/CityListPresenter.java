package com.jack.myexperience.presenter;

import com.jack.myexperience.model.CityListModel;
import com.jack.myexperience.viewinterface.CityListView;

import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2016/2/24 0024.
 */
public class CityListPresenter {
    CityListView mView;
    CityListModel mModel;
    public static Observer mObserverForProvinceList;
    public static Observer mObserverForCityList;
    public CityListPresenter(CityListView mView) {
        this.mView = mView;
        this.mModel=new CityListModel();
        init();
    }

    private void init() {
        mObserverForProvinceList=new Observer() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                if(null!=o && o instanceof List){
                    List temp=(List)o;
                    CityListPresenter.this.mView.setProvinces(temp);
                }
            }
        };
        mObserverForCityList=new Observer() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                if(null!=o && o instanceof List){
                    List temp=(List)o;
                    CityListPresenter.this.mView.setCityForProvinces(temp);
                }
            }
        };
    }

    public void getAll(){
        mModel.getAllData(mObserverForProvinceList);
    }
    public void getByProvince(String temp){
        mModel.getDataByProvince(temp, mObserverForCityList);
    }
    public void getByPosition(int position){
        mModel.getCityListByPosition(position, mObserverForCityList);
    }
}
