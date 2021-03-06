package com.rg.ui.basefragment;

import android.app.Activity;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.rg.ui.R;
import com.rg.ui.baseactivity.TBaseActivity;
import com.rg.ui.baseexception.NotFindTBaseActivityException;



/**
 * from support v4 packages
 * Created by rango on 2016/7/4.
 */
public abstract class TBaseFragment extends Fragment implements View.OnClickListener {
    private View rootView;

    private LinearLayout contentView;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.tbase_fragment_layout, null);
        contentView = rootView.findViewById(R.id.tbase_fragment_childContentLayout);
        onInitLayout(inflater, container, savedInstanceState);
        return rootView;
    }

    /**
     * 提供给子类加载器
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    public abstract void onInitLayout (LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState);

    public LinearLayout getContentView() {
        return contentView;
    }

    /**
     * 设置子类布局
     *
     * @param view
     */
    public void setContentLayout (@Nullable View view) {
        if (null != contentView && null != view) {
            if (contentView.getChildCount() > 0) {
                contentView.removeAllViews();
            }
            view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            contentView.addView(view);
        }
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
//view 对象
        rootView = null;
        contentView = null;
    }

}
