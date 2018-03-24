package me.xiaocao.news.ui.jiemian.channel;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.xiaocao.news.R;
import me.xiaocao.news.app.Constants;
import me.xiaocao.news.model.Jiemian;
import me.xiaocao.news.model.request.OtherListRequest;
import me.xiaocao.news.ui.adapter.OtherListAdapter;
import me.xiaocao.news.ui.jiemian.channel.HomeListContract.IView;
import me.xiaocao.news.ui.jiemian.detail.JiemianDetailActivity;
import x.lib.ui.BaseActivity;
import x.lib.ui.BaseMvpActivity;
import x.lib.ui.TitleView;
import x.lib.utils.FragmentUtils;
import x.lib.utils.GlideUtils;
import x.lib.utils.ToastUtils;
import x.lib.view.banner.BannerBaseAdapter;
import x.lib.view.banner.BannerView;

public class OtherListActivity extends BaseActivity  {
    @Override
    protected int setContentViewResId() {
        return R.layout.activity_other_list;
    }

    @Override
    protected void initTitle() {
        TitleView title=new TitleView(activity,findViewById(R.id.toolbar));
        title.setBack(activity);
        title.setTitleText("");
    }

    @Override
    protected void initInstance() {
        FragmentUtils.addFragment(getSupportFragmentManager(),OtherListFragment.newInstance(getIntent().getExtras().getString(Constants.JIEMIAN_NEWS_ID)),R.id.flContent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
