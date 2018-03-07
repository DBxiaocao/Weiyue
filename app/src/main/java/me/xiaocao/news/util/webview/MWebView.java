package me.xiaocao.news.util.webview;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * description: MWebView
 * author: lijun
 * date: 17/9/11 下午11:07
 */

public class MWebView extends WebView {


    private ProgressDialog dialog;

    public MWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dialog = ProgressDialog.show(context, "提示", "数据解析中。。。");
        dialog.setCancelable(true);
        dialog.show();
        setWebViewClient(new MyWebClient());
        setWebChromeClient(new ChromeClient());
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                addImageClickListener(view);
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }
    }

    public ProgressDialog getDialog() {
        return dialog;
    }


    private class MyWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }

    /**
     * 为所有的图片添加点击事件
     *
     * @param webView 对应的 WebView
     */
    private void addImageClickListener(WebView webView) {
        webView.loadUrl("javascript:(initClick())()");
    }

}
