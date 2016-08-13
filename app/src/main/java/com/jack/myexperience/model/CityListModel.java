package com.jack.myexperience.model;
import com.google.gson.Gson;
import com.jack.myexperience.MyApplication;
import com.jack.myexperience.bean.CityBean;
import com.jack.myexperience.datainterface.CityData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 可根据省名字查找 城市
 * Created by Administrator on 2016/2/24 0024.
 */
public class CityListModel implements CityData{
    private List<CityBean.Province> mProvinceLists=new ArrayList<>();
    private List<CityBean.Province.City> mProvinceCityLists=new ArrayList<>();
    @Override
    public void getAllData(final Observer observer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is= MyApplication.getInstance().getAssets().open("cityCode.json");
                    InputStreamReader read=new InputStreamReader(is);
                    BufferedReader reader=new BufferedReader(read);
                    StringBuffer buffer=new StringBuffer();
                    String str;
                    while ((str=reader.readLine())!=null){
                        buffer.append(str);
                    }
                    Gson gson=new Gson();
                    String temp = buffer.toString().substring(1,buffer.length()).replaceAll("\\s*", "").trim();
                    CityBean bean=gson.fromJson(temp,
                            CityBean.class);
                    mProvinceLists=bean.getProvinceList();
                    List send=new ArrayList();
                    send.add(mProvinceLists);
                    Observable.from(send)
                    .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    public void getDataByProvince(String name,Observer observer) {
        if(null!=mProvinceLists){
            for(int i=0;i<mProvinceLists.size();i++){
                if(mProvinceLists.get(i).getProvince().contains(name)){
                    mProvinceCityLists=mProvinceLists.get(i).getCity();
                    List send=new ArrayList();
                    send.add(mProvinceCityLists);
                    Observable.from(send)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observer);
                }
            }
        }
    }

    @Override
    public void getCityListByPosition(int position, Observer observer) {
        if(null!=mProvinceLists && mProvinceLists.size()>position){
            List send=new ArrayList();
            send.add(mProvinceLists.get(position).getCity());
            Observable.from(send)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }
}
