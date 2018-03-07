package x.lib;

import android.support.v7.app.AppCompatDelegate;

import x.lib.handler.ActivityManager;
import x.lib.handler.CrashHandler;
import x.lib.utils.SPUtils;
import x.lib.utils.Utils;

/**
 * className: MyClass
 * author: lijun
 * date: 17/5/15 17:35
 */

public class BaseApplication extends android.app.Application {
    public static BaseApplication instance;

    public static synchronized BaseApplication getInstance() {
        if (instance == null) {
            instance = new BaseApplication();
        }
        return instance;
    }

    public static ActivityManager activityManagement;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
//        CrashHandler.getInstance().init(this);
        activityManagement = ActivityManager.getInstance();
    }
}
