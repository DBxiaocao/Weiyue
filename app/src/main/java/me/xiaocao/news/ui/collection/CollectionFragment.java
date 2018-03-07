package me.xiaocao.news.ui.collection;

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
import me.xiaocao.news.app.Constants;
import me.xiaocao.news.helper.PresenterFactory;
import me.xiaocao.news.model.NewsList;
import me.xiaocao.news.model.db.CollectionVo;
import me.xiaocao.news.model.event.CollectionEvent;
import me.xiaocao.news.presenter.INewsPresenter;
import me.xiaocao.news.ui.news.NewsDetailActivity;
import me.xiaocao.news.ui.read.ReadDetailActivity;
import x.lib.ui.BaseEvent;
import x.lib.ui.BaseFragment;
import x.lib.utils.GlideUtils;
import x.lib.utils.StringUtils;

/**
 * description: CollectionFragment
 * author: lijun
 * date: 17/9/15 下午12:17
 */

public class CollectionFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private INewsPresenter mPresenter;
    private BaseListAdapter<CollectionVo> listAdapter;
    private int offset;

    @Override
    protected int setContentViewResId() {
        return R.layout.include_recycler;
    }

    @Override
    protected void initInstance() {
        mPresenter = PresenterFactory.getNewsPresenter();
        listAdapter = new BaseListAdapter<>(R.layout.list_item_news, new ArrayList<CollectionVo>());
        listAdapter.setOnCallBackData(new BaseListAdapter.OnCallBackData<CollectionVo>() {
            @Override
            public void convertView(BaseViewHolder helper, CollectionVo item) {
                helper.setText(R.id.tvNewsTitle, item.getTitle());
                helper.setText(R.id.tvNewsSource, item.getSrc());
                ImageView image = helper.getView(R.id.ivNews);
                if (!StringUtils.isEmpty(item.getImgUrl())) {
                    image.setVisibility(View.VISIBLE);
                    GlideUtils.loadImageView(activity, item.getImgUrl(), image);
                } else {
                    image.setVisibility(View.GONE);
                }
            }
        });
        recycler.setAdapter(listAdapter);
        swipeRefresh.setOnRefreshListener(this);
        listAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        listAdapter.setPreLoadNumber(1);
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (listAdapter.getItem(position).getType() == CollectionVo.zhihu) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.KEY_READ_TITLE, listAdapter.getItem(position).getTitle());
                    bundle.putInt(Constants.KEY_READ_TYPE, 0);
                    bundle.putString(Constants.KEY_READ_URL, listAdapter.getItem(position).getUrl() + "");
                    GoActivity(ReadDetailActivity.class, bundle);
                } else if (listAdapter.getItem(position).getType() == CollectionVo.guokr) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.KEY_READ_TITLE, listAdapter.getItem(position).getTitle());
                    bundle.putInt(Constants.KEY_READ_TYPE, 1);
                    bundle.putString(Constants.KEY_READ_URL, listAdapter.getItem(position).getUrl());
                    GoActivity(ReadDetailActivity.class, bundle);
                } else {
                    NewsList news = new NewsList();
                    CollectionVo collectionVo = listAdapter.getItem(position);
                    news.setTitle(collectionVo.getTitle());
                    news.setWeburl(collectionVo.getUrl());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.KEY_NEWS_DETAIL, news);
                    GoActivity(NewsDetailActivity.class, bundle);
                }
            }
        });
        listAdapter.setOnLoadMoreListener(this, recycler);
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
    public void onEvent(CollectionEvent event) {
        switch (event.code) {
            case BaseEvent.code_err:
                swipeRefresh.setRefreshing(false);
                listAdapter.loadMoreEnd();
                showErrNetWork(recycler, (String) event.data);
                break;
            case BaseEvent.code_load:
                offset++;
                listAdapter.addData((List<CollectionVo>) event.data);
                listAdapter.loadMoreComplete();
                break;
            case BaseEvent.code_load_err:
                listAdapter.loadMoreEnd();
                showErrNetWork(recycler, (String) event.data);
                break;
            case BaseEvent.code_refresh:
                offset++;
                swipeRefresh.setRefreshing(false);
                listAdapter.getData().clear();
                listAdapter.addData((List<CollectionVo>) event.data);
                listAdapter.loadMoreComplete();
                break;
        }
    }

    @Override
    public void onRefresh() {
        offset = 0;
        swipeRefresh.setRefreshing(true);
        mPresenter.getCollection(offset);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getCollection(offset);
    }
}
