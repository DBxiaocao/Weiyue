package me.xiaocao.news.ui.jiemian.channel;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.xiaocao.news.R;
import me.xiaocao.news.app.Constants;
import me.xiaocao.news.model.Jiemian;
import me.xiaocao.news.model.request.OtherListRequest;
import me.xiaocao.news.ui.adapter.OtherListAdapter;
import me.xiaocao.news.ui.jiemian.detail.JiemianDetailActivity;
import x.lib.ui.BaseMvpFragment;
import x.lib.utils.GlideUtils;
import x.lib.view.banner.BannerBaseAdapter;
import x.lib.view.banner.BannerView;

/**
 * description: OtherFragment
 * author: lijun
 * date: 18/1/4 19:45
 */

public class OtherListFragment extends BaseMvpFragment<HomeListPresenter> implements HomeListContract.IView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private OtherListAdapter adapter;
    private BannerView bannerView;
    private BannerBaseAdapter<Jiemian.CarouselEntity> bannerAdapter;

    public static OtherListFragment newInstance(String newsId) {
        OtherListFragment fragment = new OtherListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.JIEMIAN_NEWS_ID, newsId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onErrMsg(String errMsg) {

    }

    @Override
    protected int setContentViewResId() {
        return R.layout.include_recycler;
    }

    @Override
    protected void initInstance() {
        adapter = new OtherListAdapter(new ArrayList<Jiemian.ListEntityX>(), activity);
        recycler.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapters, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.NEWS_Title, adapter.getItem(position).getArticle().getAr_tl());
                bundle.putString(Constants.NEWS_ID, adapter.getItem(position).getArticle().getAr_id());
                GoActivity(JiemianDetailActivity.class, bundle);
            }
        });
        adapter.setPreLoadNumber(1);
        adapter.setOnLoadMoreListener(this, recycler);
        View headView = LayoutInflater.from(activity).inflate(R.layout.headview_other_banner, null);
        bannerView=headView.findViewById(R.id.banner);
        bannerAdapter = new BannerBaseAdapter<Jiemian.CarouselEntity>(activity) {
            @Override
            protected int getLayoutResID() {
                return R.layout.item_banner;
            }

            @Override
            protected void convert(View convertView, Jiemian.CarouselEntity data) {
                ImageView bannerIv = getView(R.id.pageImage);
                GlideUtils.loadImageView(activity, data.getArticle().getAr_image(), bannerIv);
                setText(R.id.pageText, data.getArticle().getAr_tl());
            }
        };
        bannerView.setAdapter(bannerAdapter);
        adapter.addHeaderView(headView);
    }

    private int page = 1;

    @Override
    protected void lazyLoad() {
        if (adapter != null && adapter.getData().size() <= 0) {
            onRefresh();
        }
    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    public void getCarousel(List<Jiemian.CarouselEntity> list) {
        bannerAdapter.setData(list);
    }

    @Override
    public void getRefreshList(List<Jiemian.ListEntityX> list) {
        swipeRefresh.setRefreshing(false);
        adapter.replaceData(list);
        page++;
    }

    @Override
    public void getLoadList(List<Jiemian.ListEntityX> list) {
        adapter.addData(list);
        page++;
        adapter.loadMoreComplete();
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(true);
        page = 1;
        mPresenter.refreshList(new OtherListRequest().setPage(page).setChannel(getArguments().getString(Constants.JIEMIAN_NEWS_ID)));
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadList(new OtherListRequest().setPage(page).setChannel(getArguments().getString(Constants.JIEMIAN_NEWS_ID)));
    }
}
