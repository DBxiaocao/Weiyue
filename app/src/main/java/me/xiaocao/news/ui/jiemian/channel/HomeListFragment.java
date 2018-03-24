package me.xiaocao.news.ui.jiemian.channel;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
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
import me.xiaocao.news.model.request.HomeListRequest;
import me.xiaocao.news.ui.adapter.HomeListAdapter;
import me.xiaocao.news.ui.jiemian.detail.JiemianDetailActivity;
import x.lib.ui.BaseMvpFragment;
import x.lib.utils.DateUtils;
import x.lib.utils.GlideUtils;
import x.lib.utils.LogUtil;
import x.lib.utils.ToastUtils;
import x.lib.view.banner.BannerBaseAdapter;
import x.lib.view.banner.BannerView;

/**
 * description: HomeFragment
 * author: lijun
 * date: 18/1/4 19:44
 */

public class HomeListFragment extends BaseMvpFragment<HomeListPresenter> implements HomeListContract.IView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private HomeListAdapter adapter;
    private BannerView bannerView;
    private View headView;
    private int page = 2;
    private BannerBaseAdapter<Jiemian.CarouselEntity> bannerAdapter;

    public static HomeListFragment newInstance() {
        HomeListFragment fragment = new HomeListFragment();
        return fragment;
    }

    @Override
    public void onErrMsg(String errMsg) {
        ToastUtils.showShort(activity,errMsg);
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.include_recycler;
    }

    @Override
    protected void initInstance() {
        adapter = new HomeListAdapter(new ArrayList<Jiemian.ListEntityX>(), activity);
        recycler.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setPreLoadNumber(1);
        swipeRefresh.setOnRefreshListener(this);
         headView= LayoutInflater.from(activity).inflate(R.layout.headview_other_banner, null);
        bannerView = headView.findViewById(R.id.banner);
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
        bannerAdapter.setOnPageTouchListener(new BannerBaseAdapter.OnPageTouchListener<Jiemian.CarouselEntity>() {
            @Override
            public void onPageClick(int position, Jiemian.CarouselEntity bean) {
                if (bean.getType().equals("article")){
                    goToDetail(bean.getArticle().getAr_id(),bean.getArticle().getAr_tl());
                }
            }
        });
        bannerView.setAdapter(bannerAdapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapters, View view, int position) {
                if (adapter.getItem(position).getItemType()== Jiemian.ListEntityX.Type_xiaotu){
                    Jiemian.ListEntityX.ArticleEntityX bean=adapter.getItem(position).getArticle();
                    goToDetail(bean.getAr_id(),bean.getAr_tl());
                }
            }
        });
        adapter.setPreLoadNumber(1);
        adapter.setOnLoadMoreListener(this, recycler);
    }

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
        adapter.addHeaderView(headView);
    }

    public void getRefreshList(List<Jiemian.ListEntityX> list) {
        page = 2;
        adapter.replaceData(list);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void getLoadList(List<Jiemian.ListEntityX> list) {
        adapter.addData(list);
        page++;
        adapter.loadMoreComplete();
    }

    @Override
    public void goToDetail(String newsId, String newsTitle) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NEWS_Title,newsTitle );
        bundle.putString(Constants.NEWS_ID,newsId);
        GoActivity(JiemianDetailActivity.class, bundle);
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(true);
        mPresenter.refreshList(new HomeListRequest().setIsRefresh(true));
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadList(new HomeListRequest().setIsRefresh(false).setDate(DateUtils.getTime()).setPage(page));
    }
}
