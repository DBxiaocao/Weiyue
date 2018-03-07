package me.xiaocao.news.ui.news;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;

import butterknife.Bind;
import me.xiaocao.news.R;
import me.xiaocao.news.app.Constants;
import x.lib.ui.BaseActivity;
import x.lib.ui.TitleView;

public class NewsWebActivity extends BaseActivity {

    @Bind(R.id.llContent)
    LinearLayout llContent;
    private TitleView title;
    private AgentWeb mAgentWeb;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_news_web;
    }

    @Override
    protected void initTitle() {
        title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setBack(activity);
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    protected void initInstance() {
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(llContent, new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()//
                .defaultProgressBarColor()
                .setReceivedTitleCallback(mCallback)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecutityType(AgentWeb.SecurityType.strict)
//                .setWebLayout(new WebLayout(this))
                .createAgentWeb()//
                .ready()
                .go(getIntent().getExtras().getString(Constants.KEY_NEWS_URL));
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String titles) {
            if (title != null)
                title.setTitleText(titles);
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            view.loadUrl("javascript:function setTop(){document.querySelector('#articleContent > div.left > ins').style.display=\"none\";}setTop();");
        }
    };


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:function setTop(){document.querySelector('#articleContent > div.left > ins').style.display=\"none\";}setTop();");
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
