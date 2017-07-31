package x.http.util;

import android.content.Context;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * className: UserAgentUtil
 * author: lijun
 * date: 17/7/7 15:41
 */

public class UserAgentUtil {
    public static String getUserAgent(Context context) {
        WebView webview;
        webview = new WebView(context);
        WebSettings settings = webview.getSettings();
        String str=settings.getUserAgentString();
        return settings.getUserAgentString();
    }
}
