package com.xiaocao.weiyue.ui.user;

import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaocao.weiyue.MyApp;
import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.utils.IntentUtil;

import butterknife.Bind;
import butterknife.OnClick;
import x.lib.ui.BaseFragment;
import x.lib.utils.AppUtils;
import x.lib.utils.CleanUtils;
import x.lib.utils.SpanUtils;
import x.lib.utils.ToastUtils;

/**
 * className: UserFragment
 * author: lijun
 * date: 17/6/30 10:21
 */

public class UserFragment extends BaseFragment {


    @Bind(R.id.tvSelfCollection)
    TextView tvSelfCollection;
    @Bind(R.id.rlClearCache)
    RelativeLayout tvClearCache;
    @Bind(R.id.tvCacheSize)
    TextView tvCacheSize;
    @Bind(R.id.tvAbout)
    TextView tvAbout;
    @Bind(R.id.tvVersion)
    TextView tvVersion;
    @Bind(R.id.tvUrl)
    TextView tvUrl;

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initInstance() {
        try {
            tvCacheSize.setText(CleanUtils.getCacheSize(MyApp.getInstance().getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvVersion.setText(AppUtils.getAppVersionName());
        tvUrl.setMovementMethod(LinkMovementMethod.getInstance());
        tvUrl.setText(new SpanUtils()
                .append("GitHub:")
                .appendLine("https://github.com/DBxiaocao")
                .setUrl("https://github.com/DBxiaocao")
                .setClickSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                IntentUtil.shareUrl(activity,"https://github.com/DBxiaocao");
            }
        }).create());
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.tvSelfCollection, R.id.rlClearCache, R.id.tvAbout})
    public void onClick(View view) {
//        showDialog();
        switch (view.getId()) {
            case R.id.rlClearCache:
                CleanUtils.cleanCustomCache(MyApp.getInstance().getCacheDir());
                tvCacheSize.setText("0Byte");
                break;
            case R.id.tvSelfCollection:
                GoActivity(CollectionActivity.class);
                break;
            case R.id.tvAbout:
                GoActivity(AboutActivity.class);
                break;
        }
    }
}
