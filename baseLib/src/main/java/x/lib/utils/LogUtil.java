package x.lib.utils;

import android.content.Context;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * description: LogUtil
 * author: xiaocao
 * date: 17/6/26 下午6:41
 */
public class LogUtil {
    public static void init(final boolean isLog) {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return isLog;
            }
        });
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void d(String msg) {
        Logger.d(msg);
    }


    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void i(String msg) {
        Logger.i(msg);
    }


    public static void w(String msg) {
        Logger.w(msg);
    }

    public static void v(String msg) {
        Logger.v(msg);
    }


    public static void d(String tag,String msg) {
        Logger.d(tag,msg);
    }


    public static void e(String tag,String msg) {
        Logger.e(tag,msg);
    }

    public static void i(String tag,String msg) {
        Logger.i(tag,msg);
    }


    public static void w(String tag,String msg) {
        Logger.w(tag,msg);
    }

    public static void v(String tag,String msg) {
        Logger.v(tag,msg);
    }


    public static void xml(String msg) {
        Logger.xml(msg);
    }
}
