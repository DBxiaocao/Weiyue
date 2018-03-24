//package me.xiaocao.news.ui.read;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.webkit.JavascriptInterface;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import butterknife.Bind;
//import me.xiaocao.news.R;
//import me.xiaocao.news.app.Api;
//import me.xiaocao.news.app.Constants;
//import me.xiaocao.news.db.CollectionHelper;
//import me.xiaocao.news.helper.PresenterFactory;
//import me.xiaocao.news.model.ZhihuDetail;
//import me.xiaocao.news.model.db.CollectionVo;
//import me.xiaocao.news.model.event.ReadDetailEvent;
//import me.xiaocao.news.model.request.GuokrDetailRequest;
//import me.xiaocao.news.model.request.ZhiHuDetailRequest;
//import me.xiaocao.news.ui.news.NewsWebActivity;
//import me.xiaocao.news.ui.pic.PhotoActivity;
//import me.xiaocao.news.util.IntentUtil;
//import me.xiaocao.news.util.webview.JavaScriptFunction;
//import me.xiaocao.news.util.webview.MWebView;
//import me.xiaocao.news.util.webview.WebUtils;
//import x.lib.ui.BaseActivity;
//import x.lib.ui.BaseEvent;
//import x.lib.ui.TitleView;
//import x.lib.utils.NetUtils;
//import x.lib.utils.SPUtils;
//import x.lib.utils.StringUtils;
//
///**
// * description: ReadDetailActivity
// * author: lijun
// * date: 17/9/8 下午10:51
// */
//public class ReadDetailActivity extends BaseActivity {
//
//    @Override
//    protected int setContentViewResId() {
//        return 0;
//    }
//
//    @Override
//    protected void initTitle() {
//
//    }
//
//    @Override
//    protected void initInstance() {
//
//    }
//}
