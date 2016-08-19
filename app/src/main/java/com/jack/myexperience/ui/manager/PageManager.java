package com.jack.myexperience.ui.manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Administrator on 2016/5/30.
 */
public class PageManager {
    private int mContainer;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    public static String CONVERSION = "conversion";
    public static String FRIENDS = "friends";
    public static String ORDER = "order";
    public static String ME = "me";
    private String[] TAGS = {CONVERSION, FRIENDS};

    public PageManager(int mContainer, FragmentManager fragmentManager) {
        this.mContainer = mContainer;
        this.fragmentManager = fragmentManager;
    }


    public void add(Class<? extends Fragment> clazz, String tag, Bundle bundle) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (String tagName : TAGS) {
            Fragment fragment = fragmentManager.findFragmentByTag(tagName);
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        currentFragment = null;
        if (fragment != null) {
            currentFragment = fragment;
            transaction.show(fragment);
        } else {
            try {
                fragment = clazz.newInstance();
                currentFragment = fragment;
                transaction.add(mContainer, fragment, tag);
                if (bundle != null) {
                    fragment.setArguments(bundle);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        transaction.commitAllowingStateLoss();
    }

}
