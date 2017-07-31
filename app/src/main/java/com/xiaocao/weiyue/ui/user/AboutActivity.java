package com.xiaocao.weiyue.ui.user;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.utils.IntentUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import x.lib.ui.BaseActivity;
import x.lib.ui.TitleView;
import x.lib.utils.SpanUtils;
import x.lib.utils.ToastUtils;

public class AboutActivity extends BaseActivity {


    @Bind(R.id.tvAboutProject)
    TextView tvAboutProject;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setBack(activity);
        title.setTitleText("关于");
    }

    @Override
    protected void initInstance() {
        tvAboutProject.setMovementMethod(LinkMovementMethod.getInstance());
        tvAboutProject.setText(new SpanUtils()
                .append("微阅是一款集新闻，视频和妹子的阅读类软件。如果您喜欢,欢迎star。开源地址:")
                .appendLine("https://github.com/DBxiaocao/Weiyue")
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        IntentUtil.shareUrl(activity,"https://github.com/DBxiaocao/Weiyue");
                    }
                })
                .setUrl("https://github.com/DBxiaocao/Weiyue")
                .create());

    }


}
