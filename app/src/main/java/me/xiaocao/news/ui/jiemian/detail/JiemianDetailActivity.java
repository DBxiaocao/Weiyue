package me.xiaocao.news.ui.jiemian.detail;


import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.nodes.Document;

import butterknife.Bind;
import me.xiaocao.news.R;
import me.xiaocao.news.app.Api;
import me.xiaocao.news.app.Constants;
import me.xiaocao.news.ui.pic.PhotoActivity;
import me.xiaocao.news.util.webview.JavaScriptFunction;
import me.xiaocao.news.util.webview.MWebView;
import me.xiaocao.news.util.webview.WebUtils;
import x.lib.jsoup.RxJsoupNetWork;
import x.lib.jsoup.RxJsoupNetWorkListener;
import x.lib.ui.BaseActivity;
import x.lib.ui.TitleView;
import x.lib.utils.LogUtil;
import x.lib.utils.SPUtils;

public class JiemianDetailActivity extends BaseActivity {
    @Bind(R.id.webView)
    MWebView webView;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_jiemian_detail;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setBack(activity);
        title.setTitleText(getIntent().getStringExtra(Constants.NEWS_Title));
    }


    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }


    @Override
    protected void initInstance() {
        webView.setDrawingCacheEnabled(true);
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setSavePassword(false);

        mWebSettings.setMixedContentMode(0);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        int index = SPUtils.getInstance().getInt(BaseActivity.isTextSize, 0);
        int size;
        if (index == 0) {
            size = 16;
        } else if (index == 1) {
            size = 18;
        } else {
            size = 20;
        }
        mWebSettings.setTextZoom(size);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setSupportMultipleWindows(false);
        mWebSettings.setBlockNetworkImage(false);//是否阻塞加载网络图片  协议http or https
        mWebSettings.setAllowFileAccess(true); //允许加载本地文件html  file协议, 这可能会造成不安全 , 建议重写关闭
        mWebSettings.setAllowFileAccessFromFileURLs(false); //通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
        mWebSettings.setAllowUniversalAccessFromFileURLs(false);//允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= 21)
            mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        else
            mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setNeedInitialFocus(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebSettings.setDefaultFontSize(22);
        mWebSettings.setMinimumFontSize(size);//设置 WebView 支持的最小字体大小，默认为 8
        mWebSettings.setGeolocationEnabled(true);
        //适配5.0不允许http和https混合使用情况
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //缓存文件最大值
        mWebSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webView.addJavascriptInterface(new JavaScriptFunction() {
            @Override
            @JavascriptInterface
            public void getUrl(String imageUrl) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.PHOTO_URL, imageUrl);
                GoActivity(PhotoActivity.class, bundle);
            }
        }, "JavaScriptFunction");

        RxJsoupNetWork.getInstance().getApi(new StringBuffer().append(Api.JIEMIAN_HOST_DETAIL).append(getIntent().getStringExtra(Constants.NEWS_ID)).append(Api.DETAIL_END).toString(), new RxJsoupNetWorkListener<String>() {
            @Override
            public void onNetWorkStart() {

            }

            @Override
            public void onNetWorkError(Throwable e) {

            }

            @Override
            public void onNetWorkComplete() {

            }

            @Override
            public void onNetWorkSuccess(String s) {
                String htmlData = WebUtils.buildHtmlForIt(s, false);
                webView.loadDataWithBaseURL("http://www.jiemian.com", htmlData, WebUtils.MIME_TYPE, WebUtils.ENCODING, null);
            }

            @Override
            public String getT(Document document) {
                LogUtil.d(document.select("div.article-content").toString());
                return document.select("div.article-content").toString();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
        }
    }
}
