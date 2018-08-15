package com.dream.assessment;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.dream.assessment.utils.GreenDaoHelper;
import com.facebook.stetho.Stetho;

public class App extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Utils.init(this);
        GreenDaoHelper.initDatabase();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
