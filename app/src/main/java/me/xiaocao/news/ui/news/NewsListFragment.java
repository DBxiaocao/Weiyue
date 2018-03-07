package me.xiaocao.news.ui.news;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.xiaocao.news.R;
import me.xiaocao.news.ui.adapter.BaseListAdapter;
import me.xiaocao.news.app.Api;
import me.xiaocao.news.app.Constants;
import me.xiaocao.news.helper.PresenterFactory;
import me.xiaocao.news.model.NewsList;
import me.xiaocao.news.model.event.NewsListEvent;
import me.xiaocao.news.model.request.NewsListRequest;
import me.xiaocao.news.presenter.INewsPresenter;
import x.lib.ui.BaseEvent;
import x.lib.ui.BaseFragment;
import x.lib.utils.GlideUtils;
import x.lib.utils.StringUtils;

/**
 * description: NewsListFragment
 * author: lijun
 * date: 17/8/27 14:17
 */

public class NewsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private INewsPresenter mPresenter;
    private BaseListAdapter<NewsList> listAdapter;
    private int start = 0;

    public static NewsListFragment newInstance(String id) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NEWS_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.include_recycler;
    }

    @Override
    protected void initInstance() {
        mPresenter = PresenterFactory.getNewsPresenter();
        listAdapter = new BaseListAdapter<>(R.layout.list_item_news, new ArrayList<NewsList>());
        listAdapter.setOnCallBackData(new BaseListAdapter.OnCallBackData<NewsList>() {
            @Override
            public void convertView(BaseViewHolder helper, NewsList item) {
                helper.setText(R.id.tvNewsTitle, item.getTitle());
                helper.setText(R.id.tvNewsSource, item.getSrc());
                helper.setText(R.id.tvNewsTime, item.getTime());
                ImageView image = helper.getView(R.id.ivNews);
                if (!StringUtils.isEmpty(item.getPic())) {
                    image.setVisibility(View.VISIBLE);
                    GlideUtils.loadImageView(activity, item.getPic(), image);
                } else {
                    image.setVisibility(View.GONE);
                }
            }
        });
        recycler.setAdapter(listAdapter);
        swipeRefresh.setOnRefreshListener(this);
        listAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        listAdapter.setPreLoadNumber(1);
        listAdapter.setOnLoadMoreListener(this, recycler);
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
//                if (SPUtils.getInstance().getBoolean(Constants.isWeb, false)) {
//                    bundle.putString(Constants.KEY_NEWS_URL, listAdapter.getItem(position).getWeburl());
//                    GoActivity(NewsWebActivity.class, bundle);
//                } else {
                    bundle.putSerializable(Constants.KEY_NEWS_DETAIL, listAdapter.getItem(position));
                    GoActivity(NewsDetailActivity.class, bundle);
//                }
            }
        });
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected void lazyLoad() {
        if (listAdapter.getData().size() <= 0)
            onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NewsListEvent event) {
        if (event.id.equals(getArguments().getString(Constants.NEWS_ID))) {
            switch (event.code) {
                case BaseEvent.code_err:
                    swipeRefresh.setRefreshing(false);
                    listAdapter.loadMoreFail();
                    showErrNetWork(recycler, (String) event.data);
                    break;
                case BaseEvent.code_load:
                    start += 20;
                    listAdapter.addData((List<NewsList>) event.data);
                    listAdapter.loadMoreComplete();
                    break;
                case BaseEvent.code_load_err:
                    break;
                case BaseEvent.code_refresh:
                    start += 20;
                    swipeRefresh.setRefreshing(false);
                    listAdapter.getData().clear();
                    listAdapter.addData((List<NewsList>) event.data);
                    break;
            }
        }
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(true);
        start = 0;
        mPresenter.getNewsList(new NewsListRequest(Api.NEWS_KEY, getArguments().getString(Constants.NEWS_ID), start));
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getNewsList(new NewsListRequest(Api.NEWS_KEY, getArguments().getString(Constants.NEWS_ID), start));
    }
}
