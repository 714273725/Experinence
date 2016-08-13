package com.jack.myexperience.component;

import com.jack.myexperience.bean.KettleBean;
import com.jack.myexperience.module.DependencyInjectDemoModule;
import com.jack.myexperience.ui.activity.DependencyInjectActivity;
import dagger.Component;
/**
 *  这里通过 DependencyInjectDemoModule 提供了KettleBean
 *  通过 DependencyInjectComponent 将依赖注入到需要的地方
 *  如果@Component上用@Singleton注解，则Module中提供KettleBean的方法也要用@Singleton注解
 */
//DependencyInjectDemoModule可以提供两种或以上的依赖
@Component(modules = DependencyInjectDemoModule.class)
public interface DependencyInjectComponent {
    //将 KettleBean 注入到 DependencyInjectActivity
    void inject(DependencyInjectActivity activity);
    //将 HeaterBean 注入到 KettleBean
    void inject(KettleBean kettle);
}
