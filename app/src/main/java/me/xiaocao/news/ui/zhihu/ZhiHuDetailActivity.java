package me.xiaocao.news.ui.zhihu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;

import com.google.gson.JsonElement;

import java.util.List;

import butterknife.Bind;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.xiaocao.news.R;
import me.xiaocao.news.app.Api;
import me.xiaocao.news.app.ApiService;
import me.xiaocao.news.app.Constants;
import me.xiaocao.news.model.ZhihuDetail;
import me.xiaocao.news.model.request.ZhiHuDetailRequest;
import me.xiaocao.news.ui.pic.PhotoActivity;
import me.xiaocao.news.util.webview.JavaScriptFunction;
import me.xiaocao.news.util.webview.MWebView;
import me.xiaocao.news.util.webview.WebUtils;
import x.lib.http.retrofit.RetrofitUtil;
import x.lib.ui.BaseActivity;
import x.lib.ui.TitleView;
import x.lib.utils.JsonUtil;
import x.lib.utils.NetUtils;
import x.lib.utils.ToastUtils;

/**
 * description: ReadDetailActivity
 * author: lijun
 * date: 17/9/8 下午10:51
 */
public class ZhiHuDetailActivity extends BaseActivity {

    @Bind(R.id.webView)
    MWebView webView;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_zhihu_detail;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setBack(activity);
        title.setTitleText(getIntent().getExtras().getString(Constants.KEY_READ_TITLE));
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initInstance() {
        webView.setDrawingCacheEnabled(true);
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setAppCacheEnabled(false);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        if (NetUtils.isConnected(this)) {
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }

        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setDefaultTextEncodingName("UTF-8");
        webView.addJavascriptInterface(new JavaScriptFunction() {
            @Override
            @JavascriptInterface
            public void getUrl(String imageUrl) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.PHOTO_URL, imageUrl);
                GoActivity(PhotoActivity.class, bundle);
            }
        }, "JavaScriptFunction");
        ZhiHuDetailRequest request = new ZhiHuDetailRequest();
        request.setId(getIntent().getExtras().getString(Constants.KEY_READ_URL));
        RetrofitUtil.getInstance()
                .retrofit(Api.ZHIHU_HOST)
                .create(ApiService.class)
                .getZhihuDetail(request.url(), request.params())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ZhihuDetail>() {
                    @Override
                    public void accept(ZhihuDetail bean) throws Exception {
                        String htmlData = WebUtils.buildHtmlWithCss(bean.body, bean.css, false);
                        webView.loadDataWithBaseURL(WebUtils.BASE_URL, htmlData, WebUtils.MIME_TYPE, WebUtils.ENCODING, WebUtils.FAIL_URL);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort(activity, throwable.getMessage());
                    }
                });
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();
    }
}
