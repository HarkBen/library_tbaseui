package com.rg.ui.baseactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.rg.function.utils.ActivityStackManager;
import com.rg.ui.basetitle.TBaseTitleBar;
import com.rg.ui.R;
import com.rg.function.customview.swipebaklayout.SwipeBackActivity;

/**
 * BaseActivity
 * loadBaseLayout
 * loadBaseTitleBar
 * author zhusw 2016-06-16 13:43:31
 */
public abstract class TBaseActivity extends SwipeBackActivity implements View.OnClickListener {
    /**
     * rootLayout
     */
    private LinearLayout rootLayout;
    /**
     * use to  loading childActivityLayout
     */
    private FrameLayout childContentLayout;

    @Nullable
    private TBaseTitleBar titleBar;

    /**
     * 是否添加了statusBar
     */
    private boolean isAddStatus;


    public void beforeOnCreate() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        beforeOnCreate();
        super.onCreate(savedInstanceState);
        ActivityStackManager.getINSTANCE().push(this);
        initSystemSet();
        initRootLayout();
        onLayoutLoaded(savedInstanceState);
    }

    public boolean openSwipeBack() {
        return true;
    }

    private void initSystemSet() {
        //是否添加过 statusBar 默认为 false
        isAddStatus = false;
        //初始化侧滑范围
        getSwipeBackLayout().setEdgeSize(40);
        getSwipeBackLayout().setEnableGesture(openSwipeBack());

    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 为子类提供抽象 设置子类布局
     */
    public abstract void onLayoutLoaded(Bundle savedInstanceState);


    private void initRootLayout() {
        View view = LayoutInflater.from(this).inflate(R.layout.tbase_activity_layout, null);
        rootLayout = view.findViewById(R.id.tbase_activity_rootlayout);
        childContentLayout = view.findViewById(
                R.id.tbase_activity_childContentLayout);
        titleBar = view.findViewById(R.id.tbase_activity_titleBar);
        setContentView(rootLayout);
    }


    @Nullable
    public TBaseTitleBar getTitleBar() {
        if (null != titleBar) {
            return titleBar;
        } else {
            throw new NullPointerException("TbaseTitle has been deleted!");
        }
    }


    public final void hiddenTitleBar() {
        if (null != titleBar) titleBar.setVisibility(View.GONE);
    }


    public final void showTitleBar() {
        if (null != titleBar) titleBar.setVisibility(View.VISIBLE);
    }


    protected final void reSetStatusColorRes(@ColorRes int id) {
        if (isAddStatus) {
            reSetStatusColor(getResources().getColor(id));
        }
    }

    protected final void reSetStatusColor(@ColorInt int id) {
        if (isAddStatus) {
            rootLayout.getChildAt(0).setBackgroundColor(id);
        }
    }


    /**
     * @param ff 状态栏颜透明度
     */
    protected void reSetStatusAlpha(float ff) {
        if (isAddStatus) rootLayout.getChildAt(0).setAlpha(ff);
    }


    /**
     * 从根布局删除 statusbar
     * 在子类中 就需要在当前 activity的布局文件中声明 firstSystemWindows  true
     * 来指向拉伸到状态栏的view
     */
    protected final void removeStatusBar() {
        if (isAddStatus) {
            rootLayout.removeViewAt(0);
            isAddStatus = false;
        }
    }


    /**
     * 添加 statusBar
     * 如果没有添加 statusBar则新建并添加到rootLayout
     */
    public final void addSetStatus(@ColorInt int color) {
        if (!isAddStatus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                addStatusToRootLayout(this, color);
            }
        }

    }


    /**
     * 生成一个和状态栏大小相同的矩形条
     * * @param activity 需要设置的activity
     * * @param color 状态栏颜色值
     *
     * @return 状态栏矩形条
     */
    @NonNull
    private View createStatusView(@NonNull Activity activity, @ColorInt int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            statusView.setElevation(getResources().getDimension(R.dimen.tbase_stuatusBar_elevation));
        }
        return statusView;
    }


    /**
     * 设置状态栏颜色 * * @param activity 需要设置的activity * @param color 状态栏颜色值
     */
    private void addStatusToRootLayout(@NonNull Activity activity, @ColorInt int color) {
        // 生成一个状态栏大小的矩形
        View statusView = createStatusView(activity, color);
        statusView.setFitsSystemWindows(true);
        // 添加到 rootLayout最顶层
        rootLayout.addView(statusView, 0);
        isAddStatus = true;

    }

    /**
     * 设置当前打开的Activity的布局
     */
    public final void setContentLayout(View layoutView) {
        if (childContentLayout.getChildCount() > 0) {
            childContentLayout.removeAllViews();
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        childContentLayout.addView(layoutView, childContentLayout.getChildCount(), params);
    }

    public final void setContentLayout(@LayoutRes int layoutId) {
        View view = LayoutInflater.from(this).inflate(layoutId, null);
        setContentLayout(view);
    }

}
