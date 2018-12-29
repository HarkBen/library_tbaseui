package com.rb.tb.library_tbaseui;

import android.os.Bundle;

import com.rg.ui.baseactivity.TBaseActivity;

public class MainActivity extends TBaseActivity {


    @Override
    public void onLayoutLoaded(Bundle bundle) {
        setContentLayout(R.layout.activity_main);
        addSetStatus(getResources().getColor(R.color.colorPrimaryDark));
        getTitleBar().setBackgroundResource(R.color.colorAccent);
    }
}
