package com.rg.function.utils;

import android.support.v4.app.FragmentActivity;
import android.util.Log;


import java.util.Iterator;
import java.util.Stack;

/**
 * @描述： -
 * - 页面栈管理器
 * @作者：zhusw
 * @创建时间：2018/5/10/010 12:09
 * @最后更新时间：2018/5/10/010 12:09
 */
public class ActivityStackManager {

    private static final String TAG = "ActivityStackManager";

    private static ActivityStackManager INSTANCE;

    private static Stack<FragmentActivity> stack;

    private ActivityStackManager () {
        if (null == stack)
            stack = new Stack<>();
    }

    public static ActivityStackManager getINSTANCE () {
        if (null == INSTANCE) {
            synchronized (ActivityStackManager.class) {
                if (null == INSTANCE) {
                    INSTANCE = new ActivityStackManager();
                }
            }
        }
        return INSTANCE;
    }


    public void push (FragmentActivity activity) {
        //加到栈顶
        stack.push(activity);
        Log.i(TAG, "新加 act 栈 size= " + stack.size() + " name:" + activity.getClass().getSimpleName());
    }

    public boolean currentIsTopActivity (FragmentActivity currentActivity) {
        if (null != stack && stack.size() > 0) {
            if (currentActivity.equals(stack.get(0))) {
                return true;
            }
        }
        return false;
    }

    public boolean atTopActivity (Class<? extends FragmentActivity> clz) {
        if (null != stack && stack.size() > 0) {
            FragmentActivity topActivity = stack.get(stack.size()-1);
            return (topActivity.getClass().getName().equals(clz.getName()));
        }
        return false;
    }

    public void remove (FragmentActivity activity) {
        if (null != activity && stack.contains(activity)) {
            stack.remove(activity);
        }
        Log.i(TAG, "移除 act  size= " + stack.size() + " name:" + activity.getClass().getSimpleName());
    }

    public void killByClass (Class<? extends FragmentActivity> clzName) {

        String targetName = clzName.getName();//带包名的
        Log.i(TAG, "准备 killByClass：" + targetName);

        if (null != stack && stack.size() > 0) {
            Iterator<FragmentActivity> interator = stack.iterator();
            while (interator.hasNext()) {

                FragmentActivity fxTvActivity = interator.next();

                if (null != fxTvActivity) {

                    if (targetName.equals(fxTvActivity.getClass().getName())) {

                        Log.i(TAG, "执行 killByClass：" + targetName);

                        fxTvActivity.finish();
                        remove(fxTvActivity);
                        break;
                    }
                }

            }
        }
        Log.i(TAG, "移除 act killByClass size= " + stack.size());
    }

    /**
     * 结束除指定条件外的页面
     *
     * @param clzName
     */
    public void killAllExclude (Class<? extends FragmentActivity> clzName) {
        String targetName = clzName.getName();//带包名的
        Log.i(TAG, "准备 killAllExclude：" + targetName);

        if (null != stack && stack.size() > 0) {

            for (int i = stack.size() - 1; i >= 0; i--) {
                FragmentActivity fxTvActivity = stack.get(i);
                if (null != fxTvActivity) {
                    if (!targetName.equals(fxTvActivity.getClass().getName())) {

                        fxTvActivity.finish();
                        remove(fxTvActivity);
                    }
                }

            }
        }
        Log.i(TAG, "结束所有 act killAllExclude size= " + stack.size());
    }

    public void killAll () {
        if (null != stack && stack.size() > 0) {
            for (FragmentActivity fxTvActivity : stack) {
                if (null != fxTvActivity)
                    fxTvActivity.finish();
            }
        }
        stack.clear();
        Log.i(TAG, "结束所有 act killAll size= " + stack.size());
    }

}
