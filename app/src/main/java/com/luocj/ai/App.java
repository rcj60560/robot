package com.luocj.ai;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;

/**
 * Created by Administrator on 2018/2/5/005.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
        Utils.init(this);

        //
    }
}
