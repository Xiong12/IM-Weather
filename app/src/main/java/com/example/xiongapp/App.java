package com.example.xiongapp;

import io.rong.imkit.RongIM;


/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class App extends org.litepal.LitePalApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
    }
}
