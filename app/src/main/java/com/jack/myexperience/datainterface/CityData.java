package com.jack.myexperience.datainterface;

import rx.Observer;

/**
 * Created by Administrator on 2016/2/24 0024.
 */
public interface CityData {
    void getAllData(Observer observer);
    void getDataByProvince(String name,Observer observer);
    void getCityListByPosition(int position,Observer observer);
}
