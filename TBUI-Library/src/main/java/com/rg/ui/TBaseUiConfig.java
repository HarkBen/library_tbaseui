package com.rg.ui;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;

public class TBaseUiConfig {
    public static void init(Application context){
        FileDownloader.init(context);
    }
}
