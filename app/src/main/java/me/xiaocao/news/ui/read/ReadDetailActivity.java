package me.xiaocao.news.ui.read;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import butterknife.Bind;
import me.xiaocao.news.R;
import me.xiaocao.news.app.Api;
import me.xiaocao.news.app.Constants;
import me.xiaocao.news.db.CollectionHelper;
import me.xiaocao.news.helper.PresenterFactory;
import me.xiaocao.news.model.ReadDetail;
import me.xiaocao.news.model.db.CollectionVo;
import me.xiaocao.news.model.event.ReadDetailEvent;
import me.xiaocao.news.model.request.GuokrDetailRequest;
import me.xiaocao.news.model.request.ZhiHuDetailRequest;
import me.xiaocao.news.ui.news.NewsWebActivity;
import me.xiaocao.news.ui.pic.PhotoActivity;
import me.xiaocao.news.util.IntentUtil;
import me.xiaocao.news.util.webview.JavaScriptFunction;
import me.xiaocao.news.util.webview.MWebView;
import me.xiaocao.news.util.webview.NestedWebView;
import me.xiaocao.news.util.webview.WebUtils;
import x.lib.jsoup.RxJsoupNetWork;
import x.lib.jsoup.RxJsoupNetWorkListener;
import x.lib.ui.BaseActivity;
import x.lib.ui.BaseEvent;
import x.lib.ui.TitleView;
import x.lib.utils.NetUtils;
import x.lib.utils.SPUtils;
import x.lib.utils.StringUtils;

/**
 * description: ReadDetailActivity
 * author: lijun
 * date: 17/9/8 下午10:51
 */
public class ReadDetailActivity extends BaseActivity {

    @Bind(R.id.webView)
    MWebView webView;
    private ReadDetail readDetail = new ReadDetail();

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
        if (getIntent().getExtras().getInt(Constants.KEY_READ_TYPE, 0) == 0) {
//            WebSettings settings = webView.getSettings();
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
            PresenterFactory.getZhihuPresenter().getZhiDetail(new ZhiHuDetailRequest().setId(getIntent().getExtras().getString(Constants.KEY_READ_URL)));
        } else {
            int index = SPUtils.getInstance().getInt(BaseActivity.isTextSize, 0);
            int size;
            if (index == 0) {
                size = 14;
            } else if (index == 1) {
                size = 16;
            } else {
                size = 18;
            }
            mWebSettings.setTextZoom(size);
            mWebSettings.setMinimumFontSize(size);//设置 WebView 支持的最小字体大小，默认为 8
            mWebSettings.setJavaScriptEnabled(true);
            mWebSettings.setSupportZoom(true);
            mWebSettings.setBuiltInZoomControls(false);
            mWebSettings.setSavePassword(false);

            if (Build.VERSION.SDK_INT >= 21) {
                mWebSettings.setMixedContentMode(0);
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else if (Build.VERSION.SDK_INT >= 19) {
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else if (Build.VERSION.SDK_INT < 19) {
                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }

            mWebSettings.setDatabaseEnabled(true);
            mWebSettings.setAppCacheEnabled(true);
            mWebSettings.setLoadsImagesAutomatically(true);
            mWebSettings.setSupportMultipleWindows(false);
            mWebSettings.setBlockNetworkImage(false);//是否阻塞加载网络图片  协议http or https
            mWebSettings.setAllowFileAccess(true); //允许加载本地文件html  file协议, 这可能会造成不安全 , 建议重写关闭
            mWebSettings.setAllowFileAccessFromFileURLs(false); //通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
            mWebSettings.setAllowUniversalAccessFromFileURLs(false);//允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
            mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            if (Build.VERSION.SDK_INT >= 19)
                mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            else
                mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            mWebSettings.setLoadWithOverviewMode(true);
            mWebSettings.setUseWideViewPort(true);
            mWebSettings.setDomStorageEnabled(true);
            mWebSettings.setNeedInitialFocus(true);
            mWebSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
            mWebSettings.setDefaultFontSize(22);
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
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            PresenterFactory.getGuokrPresenter().getGuokrDetail(new GuokrDetailRequest().setUrl(getIntent().getExtras().getString(Constants.KEY_READ_URL)));
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReadDetailEvent event) {
        if (event.code == BaseEvent.code_success) {
            if (getIntent().getExtras().getInt(Constants.KEY_READ_TYPE, 0) == 0) {
                readDetail = (ReadDetail) event.data;
                String htmlData = WebUtils.buildHtmlWithCss(readDetail.getBody(), readDetail.getCss(), false);
                webView.loadDataWithBaseURL(WebUtils.BASE_URL, htmlData, WebUtils.MIME_TYPE, WebUtils.ENCODING, WebUtils.FAIL_URL);

//                webView.loadUrl("http://www.jiemian.com/article/1860178.html");


            } else {
                readDetail = (ReadDetail) event.data;
                String htmlData = WebUtils.buildHtmlForIt(readDetail.getContent(), false);
                webView.loadDataWithBaseURL(null, htmlData, WebUtils.MIME_TYPE, WebUtils.ENCODING, null);
            }
            supportInvalidateOptionsMenu();
        } else {
            showErrNetWork(webView, (String) event.data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_detail, menu);
        if (getIntent().getExtras().getInt(Constants.KEY_READ_TYPE, 0) == 0) {//知乎
            if (readDetail != null && !StringUtils.isEmpty(readDetail.getBody())) {
                String zhihuUrl = new StringBuffer().append(Api.ZHIHU_HOST).append(Api.ZHIHU_MSG_DETAIL).append(readDetail.getId()).toString();
                CollectionVo dbVo = CollectionHelper.queryWebUrl(zhihuUrl);
                if (null != dbVo && dbVo.getUrl().equals(zhihuUrl)) {
                    menu.getItem(0).setTitle("取消收藏");
                } else {
                    menu.getItem(0).setTitle("添加收藏");
                }
            }

        } else {
            if (readDetail != null && !StringUtils.isEmpty(readDetail.getContent())) {
                CollectionVo dbVo = CollectionHelper.queryWebUrl(readDetail.getResource_url());
                if (null != dbVo && dbVo.getUrl().equals(readDetail.getResource_url())) {
                    menu.getItem(0).setTitle("取消收藏");
                } else {
                    menu.getItem(0).setTitle("添加收藏");
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (getIntent().getExtras().getInt(Constants.KEY_READ_TYPE, 0) == 0) {
            String zhihuUrl = new StringBuffer().append(Api.ZHIHU_HOST).append(Api.ZHIHU_MSG_DETAIL).append(readDetail.getId()).toString();
            int id = item.getItemId();
            if (id == R.id.menuShare) {//分享
                IntentUtil.shareText(activity, "分享至", readDetail.getShare_url());
            } else if (id == R.id.menuCollection) {//收藏
                CollectionVo dbVo = CollectionHelper.queryWebUrl(zhihuUrl);
                if (null != dbVo && dbVo.getUrl().equals(zhihuUrl)) {
                    CollectionHelper.deleteChannel(dbVo.getId());
                    showSnackbar(webView, "已取消收藏");
                } else {
                    CollectionVo vo = new CollectionVo();
                    vo.setUrl(zhihuUrl);
                    vo.setImgUrl(readDetail.getImage());
                    vo.setTitle(readDetail.getTitle());
                    vo.setSrc("");
                    vo.setId(CollectionHelper.queryAll().size() + 1);
                    vo.setType(CollectionVo.zhihu);
                    CollectionHelper.insert(vo);
                    showSnackbar(webView, "添加收藏成功");
                }
            }else {
                Bundle bundle=new Bundle();
                bundle.putSerializable(Constants.KEY_NEWS_URL, readDetail.getShare_url());
                GoActivity(NewsWebActivity.class, bundle);
            }
        } else {
            int id = item.getItemId();
            if (id == R.id.menuShare) {//分享
                IntentUtil.shareText(activity, "分享至", readDetail.getResource_url());
            } else if (id == R.id.menuCollection) {//收藏
                CollectionVo dbVo = CollectionHelper.queryWebUrl(readDetail.getResource_url());
                if (null != dbVo && dbVo.getUrl().equals(readDetail.getResource_url())) {
                    CollectionHelper.deleteChannel(dbVo.getId());
                    showSnackbar(webView, "已取消收藏");
                } else {
                    CollectionVo vo = new CollectionVo();
                    vo.setUrl(readDetail.getResource_url());
                    vo.setImgUrl(readDetail.getSmall_image());
                    vo.setTitle(readDetail.getTitle());
                    vo.setSrc("");
                    vo.setId(CollectionHelper.queryAll().size() + 1);
                    vo.setType(CollectionVo.guokr);
                    CollectionHelper.insert(vo);
                    showSnackbar(webView, "添加收藏成功");
                }
            }else {
//                Bundle bundle=new Bundle();
//                bundle.putSerializable(Constants.KEY_NEWS_URL, readDetail.getUrl());
//                GoActivity(NewsWebActivity.class, bundle);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(readDetail.getUrl());
                intent.setData(content_url);
                startActivity(intent);
            }
        }
        supportInvalidateOptionsMenu();
        return true;
    }

    @Override
    protected void onDestroy() {
        if (webView!=null) {
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();
    }
}
