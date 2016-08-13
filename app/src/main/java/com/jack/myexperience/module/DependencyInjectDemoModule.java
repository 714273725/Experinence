package com.jack.myexperience.module;

import com.jack.myexperience.bean.HeaterBean;
import com.jack.myexperience.bean.KettleBean;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/2/24 0024.
 */
@Module
public class DependencyInjectDemoModule {
    @Provides
    HeaterBean provideHeater(){
        return new HeaterBean("加热器");
    }
    @Provides
    KettleBean providerKettle(HeaterBean heater){
        return new KettleBean(heater);
    }
}
