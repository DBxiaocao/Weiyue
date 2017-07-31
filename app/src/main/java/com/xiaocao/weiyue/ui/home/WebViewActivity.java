package com.xiaocao.weiyue.ui.home;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xiaocao.weiyue.Constants;
import com.xiaocao.weiyue.R;

import java.lang.reflect.InvocationTargetException;

import butterknife.Bind;
import butterknife.ButterKnife;
import x.lib.ui.BaseActivity;
import x.lib.ui.TitleView;

public class WebViewActivity extends BaseActivity {


    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolBar));
        title.setBack(this);
        title.setTitleText("就是个title");
    }

    @Override
    protected void initInstance() {
        // 在当前的浏览器中响应
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webView.setWebChromeClient(new WebChromeClient());
        webView.clearCache(true);//支持缓存
        webView.loadUrl(getIntent().getExtras().getString(Constants.NEWS_HTML));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {
//                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
//                } else {
//                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
//                    progressBar.setProgress(newProgress);//设置进度值
//                }
//
//            }
//        });
    }

    @Override
    protected void onPause() {
        try {
            webView.getClass().getMethod("onPause").invoke(webView,  (Object[])null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        try {
            webView.getClass().getMethod("onResume").invoke(webView,(Object[])null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
