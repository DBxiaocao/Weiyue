package x.lib;

import x.lib.handler.ActivityManager;
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
        activityManagement = ActivityManager.getInstance();
        Utils.init(this);
    }
}
