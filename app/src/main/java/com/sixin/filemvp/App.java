package com.sixin.filemvp;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class App extends Application {

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        mRefWatcher = setupLeakCanary();
    }

    private RefWatcher setupLeakCanary(){
        if(LeakCanary.isInAnalyzerProcess(this)){
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        App app = (App) context.getApplicationContext();
        return app.mRefWatcher;
    }
}
