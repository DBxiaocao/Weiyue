package me.xiaocao.news.util.webview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * description: MWebView
 * author: lijun
 * date: 17/9/11 下午11:07
 */

public class MWebView extends WebView {

    private Context mContext;
    private ProgressDialog dialog;

    public MWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
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
            }
        }
    }

    public ProgressDialog getDialog() {
        return dialog;
    }


    private class MyWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            Intent intent = new Intent();//webview 内置超链接  使用内置浏览器打开
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            mContext.startActivity(intent);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
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
