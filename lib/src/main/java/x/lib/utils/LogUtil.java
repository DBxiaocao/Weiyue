package x.lib.utils;

import android.content.Context;
import android.util.Log;

/**
 * description: LogUtil
 * author: xiaocao
 * date: 17/6/26 下午6:41
 */
public class LogUtil {
    private static String TAG = "appLog";
    private static boolean logable;

    public static void allowLog(Context context, boolean allowLog) {
        TAG = context.getPackageName();
        logable = allowLog;
    }

    public static void info(String info) {
        if (logable) {
            Log.i(TAG, info);
        }
    }

    public static void info(String tag, String info) {
        if (logable) {
            Log.i(tag, info);
        }
    }

    public static void debug(String debug) {
        if (logable) {
            Log.d(TAG, debug);
        }
    }

    public static void err(String err) {
        if (logable) {
            Log.e(TAG, err);
        }
    }

    public static void err(String tag, String err) {
        if (logable) {
            Log.e(tag, err);
        }
    }
}
