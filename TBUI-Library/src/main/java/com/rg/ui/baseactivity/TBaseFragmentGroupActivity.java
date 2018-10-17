package com.rg.ui.baseactivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.rg.ui.basefragment.TBaseFragment;

import java.io.Serializable;

/**
 * 切换Fragment的Activity
 * author rango 2016-06-16 13:43:31
 */
public abstract class TBaseFragmentGroupActivity extends TBaseActivity {
    private TBaseFragment showFragment;

    public final static String BEAN = "Serializable_BEAN";
    public final static String STRING = "Serializable_STRING";

    @Override
    public void onLayoutLoaded(Bundle savedInstanceState) {
        super.setContentLayout(setLayoutView());
        onLayoutloaded();
    }

    /**
     * 在布局加载完成以后调用
     */
    public abstract void onLayoutloaded();

    /**
     * @return FrameLayout  view id
     */
    public abstract int fragmentContainerId();

    /**
     * @return layout View
     */
    public abstract View setLayoutView();

    public TBaseFragment switchFragment(@NonNull Class<? extends TBaseFragment> clazz) {
        return switchFragment(fragmentContainerId(), clazz);
    }


    @NonNull
    public TBaseFragment findFragmentByTag(@NonNull Class clazz) {
        FragmentManager fm = getSupportFragmentManager();
        return (TBaseFragment) fm.findFragmentByTag(clazz.getName());
    }

    /**
     * @param frameId frameLayout container
     * @param clazz   position fragment
     * @return current show fragment
     */
    private TBaseFragment switchFragment(int frameId, @NonNull Class<? extends TBaseFragment> clazz) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        TBaseFragment currentFragment = (TBaseFragment) fm.findFragmentByTag(clazz.getName());
        if (null == currentFragment) {
            try {
                currentFragment = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //如果选择的fragment 不是第一个也不是正在显示的 则隐藏正在显示的
        if (null != showFragment && showFragment != currentFragment) {
            ft.hide(showFragment);
        }
        //对选择的fragment 处理
        if (!currentFragment.isAdded()) {
            ft.add(frameId, currentFragment, clazz.getName());
        } else {
            ft.show(currentFragment);
        }
        ft.commitAllowingStateLoss();
        showFragment = currentFragment;
        return currentFragment;
    }

    /**
     * 隐藏一个Fragment
     *
     * @param clazz
     */
    public void hideFragment(@NonNull Class<? extends TBaseFragment> clazz) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        TBaseFragment currentFragment = (TBaseFragment) fm.findFragmentByTag(clazz.getName());
        if (!currentFragment.isHidden()) {
            ft.hide(currentFragment);
            ft.commit();
        }
    }


    /**
     * 移除一个Fragment
     *
     * @param clazz
     */
    public void removeFragment(@NonNull Class<? extends TBaseFragment> clazz) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        TBaseFragment currentFragment = (TBaseFragment) fm.findFragmentByTag(clazz.getName());
        ft.remove(currentFragment);
        ft.commit();
    }



}
