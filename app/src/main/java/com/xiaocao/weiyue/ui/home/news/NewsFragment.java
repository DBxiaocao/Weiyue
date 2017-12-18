package com.xiaocao.weiyue.ui.home.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import butterknife.Bind;
import x.lib.ui.BaseMvpFragment;
import x.lib.utils.GlideUtils;
import x.lib.utils.ToastUtils;

import com.xiaocao.weiyue.Constants;
import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.model.News;

import com.xiaocao.weiyue.model.request.NewsRequest;
import com.xiaocao.weiyue.ui.adapter.CommAdapter;
import com.xiaocao.weiyue.ui.home.detail.NewsDetailActivity;
import com.xiaocao.weiyue.ui.home.news.NewsContract.NewsView;

import java.util.ArrayList;
import java.util.List;

/**
 * className: NewsFragment
 * author: lijun
 * date: 17/7/4 13:46
 */

public class NewsFragment extends BaseMvpFragment<NewsPresenterImpl> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener,NewsView {
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private CommAdapter<News> newsAdapter;
    private List<News> newsList = new ArrayList<>();
    private int limit;

    public static NewsFragment newInstance(String newsId) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NEWS_ID, newsId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initInstance() {
        newsAdapter = new CommAdapter<>(R.layout.list_item_news, newsList);
        newsAdapter.setOnCallBackData(new CommAdapter.OnCallBackData<News>() {
            @Override
            public void convertView(BaseViewHolder helper, News item) {
                helper.setText(R.id.tvNewsTitle, item.getTitle());
                helper.setText(R.id.tvNewsSource, item.getSource());
                helper.setText(R.id.tvNewsTime, item.getPtime());
                GlideUtils.loadImageView(activity, item.getImgsrc(), (ImageView) helper.getView(R.id.ivNews));

            }
        });
        recycler.setAdapter(newsAdapter);
        swipeRefresh.setOnRefreshListener(this);
        newsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        newsAdapter.setPreLoadNumber(1);
        newsAdapter.setOnLoadMoreListener(this, recycler);
        newsAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void lazyLoad() {
        if (newsAdapter!=null && newsAdapter.getData().size() <= 0) {
            onRefresh();
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onRefresh() {
        limit = 0;
        swipeRefresh.setRefreshing(true);
        mPresenter.getNews(new NewsRequest().setId(getArguments().getString(Constants.NEWS_ID)).setLimit(limit));
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getNews(new NewsRequest().setId(getArguments().getString(Constants.NEWS_ID)).setLimit(limit));
    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(BaseEvent event) {
//        if (event.id == getArguments().getString(Constants.NEWS_ID)) {
//            Logger.e("打印了数据" + event.code);
//            switch (event.code) {
//                case BaseEvent.code_success:
//                    break;
//                case BaseEvent.code_load_err:
//                    newsAdapter.loadMoreEnd();
//                    break;
//                case BaseEvent.code_refresh:
//                    limit += 20;
//                    swipeRefresh.setRefreshing(false);
//                    newsAdapter.getData().clear();
//                    newsAdapter.addData((List<News>) event.data);
//                    break;
//                case BaseEvent.code_load:
//                    limit += 20;
//                    newsAdapter.addData((List<News>) event.data);
//                    newsAdapter.loadMoreComplete();
//                    break;
//                case BaseEvent.code_err:
//                    ToastUtils.showShort(activity, (String) event.data);
//                    break;
//            }
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NEWS_Title, newsAdapter.getItem(position).getTitle());
        bundle.putString(Constants.NEWS_Img, newsAdapter.getItem(position).getImgsrc());
        bundle.putString(Constants.NEWS_HTML, newsAdapter.getItem(position).getUrl_3w());
        bundle.putString(Constants.NEWS_ID, newsAdapter.getItem(position).getPostid());
        GoActivity(NewsDetailActivity.class, bundle);
    }

    @Override
    public void onErrMsg(String errMsg) {
        ToastUtils.showShort(activity,errMsg);
    }

    @Override
    public void setNewsRefresh(List<News> list) {
        limit += 20;
        swipeRefresh.setRefreshing(false);
        newsAdapter.setNewData(list);
    }

    @Override
    public void setNewsLoad(List<News> list) {
        limit += 20;
        newsAdapter.addData(list);
        newsAdapter.loadMoreComplete();
    }
}