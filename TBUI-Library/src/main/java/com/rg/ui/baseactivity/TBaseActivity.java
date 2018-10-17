package com.rg.ui.baseactivity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rg.function.customview.swipebaklayout.SwipeBackActivity;
import com.rg.function.utils.ActivityStackManager;
import com.rg.ui.R;
import com.rg.ui.basetitle.TBaseTitleBar;

/**
 * Create on 2017/7/21.
 * github  https://github.com/HarkBen
 * Description:
 * ----- 关于统一规格 titleBar 请使用 R.dimens.tbase_titleBarLayout_height ------
 * author RangoBen
 * Last_Update - 2016-06-16 13:43:31
 */
public abstract class TBaseActivity extends SwipeBackActivity implements View.OnClickListener{
    private static final String TAG = "TBaseActivity";

    /**
     * use to  loading childActivityLayout
     */
    private FrameLayout childContentLayout;

    private TBaseTitleBar titleBar;


    @CallSuper
    public void beforeOnCreate(){
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        beforeOnCreate();
        super.onCreate(savedInstanceState);
        ActivityStackManager.getINSTANCE().push(this);
        initSystemSet();
        initRootLayout();
        onLayoutLoaded(savedInstanceState);


    }

    @Override
    public void onClick(View view) {

    }

    public boolean openSwipeBack () {
        return true;
    }

    private void initSystemSet () {
        //初始化侧滑范围
        getSwipeBackLayout().setEdgeSize(40);
        getSwipeBackLayout().setEnableGesture(openSwipeBack());
    }

    /**
     * 为子类提供抽象 设置子类布局
     */
    public abstract void onLayoutLoaded (Bundle savedInstanceState);


    private void initRootLayout () {
        View rootView = LayoutInflater.from(this).inflate(R.layout.tbase_activity_layout, null);
        childContentLayout =rootView.findViewById(
                R.id.tbase_activity_childContentLayout);
        titleBar =rootView.findViewById(R.id.tbase_activity_titleBar);

        setContentView(rootView);
    }

    @Nullable
    public TBaseTitleBar getTitleBar () {
        if (null != titleBar) {
            return titleBar;
        } else {
            throw new NullPointerException("TbaseTitle has been deleted!");
        }
    }


    public final void hiddenTitleBar () {
        if (null != titleBar) titleBar.setVisibility(View.GONE);
    }


    public final void showTitleBar () {
        if (null != titleBar) titleBar.setVisibility(View.VISIBLE);
    }


    /**
     * 设置当前打开的Activity的布局
     */
    public final void setContentLayout (View view) {
        if (childContentLayout.getChildCount() > 0) {
            childContentLayout.removeAllViews();
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        childContentLayout.addView(view, childContentLayout.getChildCount(), params);
    }

    public final void setContentLayout (@LayoutRes int layoutId) {
        View view = LayoutInflater.from(this).inflate(layoutId, null);
        setContentLayout(view);
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        Log.i(TAG,"onDestroy");

        ActivityStackManager.getINSTANCE().remove(this);
        //view 对象
          childContentLayout= null;
          titleBar= null;

    }

}