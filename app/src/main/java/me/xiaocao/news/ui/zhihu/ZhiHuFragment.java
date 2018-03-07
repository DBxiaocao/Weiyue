package me.xiaocao.news.ui.zhihu;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.xiaocao.news.R;
import me.xiaocao.news.ui.adapter.BaseListAdapter;
import me.xiaocao.news.app.Constants;
import me.xiaocao.news.model.Zhihu;
import me.xiaocao.news.model.request.ZhihuListRequest;
import me.xiaocao.news.ui.read.ReadDetailActivity;
import x.lib.ui.BaseMvpFragment;
import x.lib.utils.GlideUtils;
import x.lib.utils.StringUtils;
import x.lib.utils.ToastUtils;

/**
 * description: ZhiHuFragment
 * author: lijun
 * date: 18/1/3 19:48
 */

public class ZhiHuFragment extends BaseMvpFragment<ZhiHuPresenterImpl> implements ZhiHuContract.IView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private int time = 20171010;
    private BaseListAdapter<Zhihu> listAdapter;


    @Override
    protected int setContentViewResId() {
        return R.layout.include_recycler;
    }

    @Override
    protected void initInstance() {
        listAdapter = new BaseListAdapter<>(R.layout.list_item_news, new ArrayList<Zhihu>());
        listAdapter.setOnCallBackData(new BaseListAdapter.OnCallBackData<Zhihu>() {
            @Override
            public void convertView(BaseViewHolder helper, Zhihu item) {
                helper.setText(R.id.tvNewsTitle, item.getTitle());
                ImageView image = helper.getView(R.id.ivNews);
                if (item.getImages() != null && item.getImages().size() > 0) {
                    if (!StringUtils.isEmpty(item.getImages().get(0))) {
                        image.setVisibility(View.VISIBLE);
                        GlideUtils.loadImageView(activity, item.getImages().get(0), image);
                    } else {
                        image.setVisibility(View.GONE);
                    }
                } else {
                    image.setVisibility(View.GONE);
                }

            }
        });
        recycler.setAdapter(listAdapter);
        swipeRefresh.setOnRefreshListener(this);
        listAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        listAdapter.setPreLoadNumber(0);
        listAdapter.setOnLoadMoreListener(this, recycler);
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.KEY_READ_TITLE, listAdapter.getItem(position).getTitle());
                bundle.putInt(Constants.KEY_READ_TYPE, 0);
                bundle.putString(Constants.KEY_READ_URL, listAdapter.getItem(position).getId() + "");
                GoActivity(ReadDetailActivity.class, bundle);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        onRefresh();
    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    public void onErrMsg(String errMsg) {
        ToastUtils.showShort(activity, errMsg);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(true);
        mPresenter.refreshData(new ZhihuListRequest().setToDay(true));
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadData(new ZhihuListRequest().setToDay(false).setPage(time));
    }


    @Override
    public void onRefreshData(List<Zhihu> list) {
        listAdapter.replaceData(list);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onLoadData(List<Zhihu> list) {
        listAdapter.addData(list);
        listAdapter.loadMoreComplete();
        time--;
    }
}
