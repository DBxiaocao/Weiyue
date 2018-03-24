package me.xiaocao.news.app;

import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import me.xiaocao.news.BuildConfig;
import me.xiaocao.news.db.CollectionHelper;
import x.lib.BaseApplication;
import x.lib.utils.AppUtils;

/**
 * description: MyApp
 * author: lijun
 * date: 17/8/27 14:38
 */

public class MyApp extends BaseApplication {
    public static final String TX_BUG_KEY = "ee2057a004";
    public static final String DB_NAME="jianjie_news.db";

    @Override
    public void onCreate() {
        super.onCreate();
        //tx错误统计
        String processName = AppUtils.getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setUploadProcess(processName == null || processName.equals(this.getPackageName()));
        CrashReport.initCrashReport(getApplicationContext(), TX_BUG_KEY, BuildConfig.DEBUG, strategy);
        //log配置
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        CollectionHelper.initDatabase(DB_NAME);
    }
}
