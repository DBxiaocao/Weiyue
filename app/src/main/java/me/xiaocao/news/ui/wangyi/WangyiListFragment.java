package me.xiaocao.news.ui.wangyi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.xiaocao.news.R;
import me.xiaocao.news.ui.adapter.BaseListAdapter;
import me.xiaocao.news.app.Constants;
import me.xiaocao.news.helper.PresenterFactory;
import me.xiaocao.news.model.Wangyi;
import me.xiaocao.news.model.event.WangyiEvent;
import me.xiaocao.news.model.request.WangyiListRequest;
import me.xiaocao.news.presenter.INewsPresenter;
import x.lib.ui.BaseEvent;
import x.lib.ui.BaseFragment;
import x.lib.utils.GlideUtils;
import x.lib.utils.ToastUtils;

/**
 * className: WangyiListFragment
 * author: lijun
 * date: 17/7/4 13:46
 */

public class WangyiListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private INewsPresenter mPresenter;
    //    private NewsAdapter newsAdapter;
    private BaseListAdapter<Wangyi> newsAdapter;
    private List<Wangyi> newsList = new ArrayList<>();
    private int limit;

    public static WangyiListFragment newInstance(String newsId) {
        WangyiListFragment fragment = new WangyiListFragment();
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
        return R.layout.fragment_list;
    }

    @Override
    protected void initInstance() {
//        newsAdapter = new NewsAdapter(R.layout.list_item_news, newsList);
        newsAdapter = new BaseListAdapter<>(R.layout.list_item_news, newsList);
        newsAdapter.setOnCallBackData(new BaseListAdapter.OnCallBackData<Wangyi>() {
            @Override
            public void convertView(BaseViewHolder helper, Wangyi item) {
                helper.setText(R.id.tvNewsTitle, item.getTitle());
                helper.setText(R.id.tvNewsSource, item.getSource());
                helper.setText(R.id.tvNewsTime, item.getPtime());
                GlideUtils.loadImageView(activity, item.getImgsrc(), (ImageView) helper.getView(R.id.ivNews));

            }
        });
        recycler.setAdapter(newsAdapter);
        swipeRefresh.setOnRefreshListener(this);
        mPresenter = PresenterFactory.getNewsPresenter();
        newsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        newsAdapter.setPreLoadNumber(1);
        newsAdapter.setOnLoadMoreListener(this, recycler);
        newsAdapter.setOnItemClickListener(this);
    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected void lazyLoad() {
        if (newsAdapter.getData().size() <= 0) {
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
        mPresenter.getNewsList(new WangyiListRequest().setId(getArguments().getString(Constants.NEWS_ID)).setLimit(limit));
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getNewsList(new WangyiListRequest().setId(getArguments().getString(Constants.NEWS_ID)).setLimit(limit));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WangyiEvent event) {
        if (event.id == getArguments().getString(Constants.NEWS_ID)) {
            Logger.e("打印了数据" + event.code);
            switch (event.code) {
                case BaseEvent.code_success:
                    break;
                case BaseEvent.code_load_err:
                    newsAdapter.loadMoreEnd();
                    break;
                case BaseEvent.code_refresh:
                    limit += 20;
                    swipeRefresh.setRefreshing(false);
                    newsAdapter.getData().clear();
                    newsAdapter.addData((List<Wangyi>) event.data);
                    break;
                case BaseEvent.code_load:
                    limit += 20;
                    newsAdapter.addData((List<Wangyi>) event.data);
                    newsAdapter.loadMoreComplete();
                    break;
                case BaseEvent.code_err:
                    ToastUtils.showShort(activity, (String) event.data);
                    break;
            }
        }
    }

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
        GoActivity(WangyiDetailActivity.class, bundle);
    }

}
