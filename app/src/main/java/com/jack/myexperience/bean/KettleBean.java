package com.jack.myexperience.bean;

import com.jack.myexperience.component.DaggerDependencyInjectComponent;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/1/11 0011.
 */
//Inject通过Component获取依赖对象
public class KettleBean {
    HeaterBean heater1;
    @Inject
    HeaterBean heater2;
    @Inject
    public KettleBean(HeaterBean heater) {
        //自动注入
        this.heater1 = heater;
        //手动注入  如果没有这一句 heater2 为空
        DaggerDependencyInjectComponent.create().inject(this);
        heater1.setName("加热器一");
        heater2.setName("加热器二");
    }
    /**
     * 建议的写法是。。。
     * HeaterBean heater1;
     * HeaterBean heater2;
     *
     * @Inject public KettleBean(HeaterBean heater1,HeaterBean heater2) {
     * this.heater1=heater1;
     * this.heater2=heater2;
     * }
     * }
     */
    public void on() {
        if (heater1 != null)
            heater1.on();
    }
    public void off(){
        if (heater1 != null)
            heater1.off();
        if (heater2 != null)
            heater2.on();
        if (heater2 != null)
            heater2.off();
    }
}
