package xyz.yhsj.videoparse.ui;

import android.app.Application;

import org.xutils.x;

/**
 * Created by LOVE on 2016/2/3.
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(true);


    }
}
