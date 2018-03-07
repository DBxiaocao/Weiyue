package me.xiaocao.news.ui.about;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.xiaocao.news.R;
import me.xiaocao.news.util.AlipayUtil;
import me.xiaocao.news.util.IntentUtil;
import x.lib.ui.BaseActivity;
import x.lib.ui.TitleView;
import x.lib.utils.AppUtils;
import x.lib.utils.DialogUtil;
import x.lib.utils.SPUtils;
import x.lib.utils.SnackbarUtils;
import x.lib.utils.ToastUtils;

public class AboutActivity extends BaseActivity {


    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.tvSang)
    TextView tvSang;
    @Bind(R.id.tvVersion)
    TextView tvVersion;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setBack(activity);
        title.setTitleText("关于");
        title.getToolar().setOverflowIcon(getResources().getDrawable(android.R.drawable.ic_menu_share));
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    protected void initInstance() {
        tvVersion.setText(AppUtils.getAppVersionName());
    }


    @OnClick(R.id.tvSang)
    public void onViewClicked() {

        DialogUtil.showDialog(activity).setTitle("作者提示").setMessage("要跳转到支付宝打赏吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (AlipayUtil.hasInstalledAlipayClient(activity)) {
                    AlipayUtil.startAlipayClient(activity, "FKX05504L9FFZR3BDWR852");  //第二个参数代表要给被支付的二维码code  可以在用草料二维码在线生成
                } else {
                    SnackbarUtils.with(tvSang)
                            .setMessage("您还没有安装支付宝客户端,先去安装吧...^v^")
                            .setMessageColor(getResources().getColor(android.R.color.white))
                            .setBgColor(getResources().getIntArray(R.array.colors)[SPUtils.getInstance().getInt(BaseActivity.isTheme, 0)])
                            .show();
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            IntentUtil.shareText(activity,"分享至","https://www.coolapk.com/apk/me.xiaocao.news");
        }
        return true;
    }
}
