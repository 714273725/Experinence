package com.jack.myexperience.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.jack.myexperience.R;
import com.jack.myexperience.bean.KettleBean;
import com.jack.myexperience.component.DaggerDependencyInjectComponent;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 依赖注入demo
 * !依赖注入只能注入自定义的类，不能注入第三方类和系统类
 * !需要排除系统注解类 在build.gradle中 添加exclude 'META-INF/services/javax.annotation.processing.Processor'
 * 1.添加apt: build.gradle中的 build script 中的 dependencies 中添加
 *   classpath 'com.neenbedankt.gradle.plugins:android-apt:1.4'
 *   并在 build.gradle 上添加 apply plugin: 'com.neenbedankt.android-apt'
 * 2.添加 javax.annotation 依赖
 * 3.添加 google.dagger-compiler 依赖
 * 4.添加 google.dagger-2.0.2 依赖
 * 5.构建 module 提供依赖
 * 6.构建 component 注入依赖
 *
 */
public class DependencyInjectActivity extends Activity {
    @Inject
    KettleBean kettle;
    @BindView(R.id.tv_dependency_inject)
    TextView mTips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dependencyinject);
        ButterKnife.bind(this);
        //使用Component 注入依赖  此类编译一次后出现
        DaggerDependencyInjectComponent.create().inject(this);
        kettle.on();
        kettle.off();
    }
}
