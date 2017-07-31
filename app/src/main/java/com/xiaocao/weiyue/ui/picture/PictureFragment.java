package com.xiaocao.weiyue.ui.picture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import x.lib.ui.BaseFragment;
import x.lib.utils.GlideUtils;
import x.lib.utils.ToastUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.xiaocao.weiyue.Constants;
import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.helper.PresenterFactory;
import com.xiaocao.weiyue.model.Pics;
import x.lib.ui.BaseEvent;
import com.xiaocao.weiyue.model.event.PicEvent;
import com.xiaocao.weiyue.model.request.PicRequest;
import com.xiaocao.weiyue.presenter.IPicPresenter;
import com.xiaocao.weiyue.ui.adapter.MyBaseAdapter;
import x.lib.utils.EventBusUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * className: Picturefragment
 * author: lijun
 * date: 17/6/30 10:20
 */

public class PictureFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private IPicPresenter iPicPresenter;
    private PicAdapter picAdapter;
    private int page = 1;
    private List<Pics> picses = new ArrayList<>();

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_picture;
    }

    @Override
    protected void initInstance() {
        iPicPresenter = PresenterFactory.getListPicPresenter();
        picAdapter = new PicAdapter(R.layout.grid_item_pic, picses);
        recycler.setAdapter(picAdapter);
        recycler.setLayoutManager(new GridLayoutManager(activity, 2));
        swipeRefresh.setOnRefreshListener(this);
        picAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        picAdapter.setOnLoadMoreListener(this, recycler);
        picAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putString(Constants.PHOTO_URL, picAdapter.getItem(position).getImgUrl());
                GoActivity(PhotoActivity.class,bundle);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        if (picAdapter.getData().size() <= 0) {
            onRefresh();
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onRefresh() {
        page = 1;
        swipeRefresh.setRefreshing(true);
        iPicPresenter.loadPic(new PicRequest().setPager_offset(page));
    }

    @Override
    public void onLoadMoreRequested() {
        if (picAdapter.getData().size() < 10) {
            return;
        }
        iPicPresenter.loadPic(new PicRequest().setPager_offset(page));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PicEvent event) {
        Logger.e("打印了数据" + event.code);
        switch (event.code) {
            case BaseEvent.code_success:
                break;
            case BaseEvent.code_load_err:
                picAdapter.loadMoreEnd();
                break;
            case BaseEvent.code_refresh:
                page++;
                swipeRefresh.setRefreshing(false);
                picAdapter.getData().clear();
                picAdapter.addData((List<Pics>) event.data);
                break;
            case BaseEvent.code_load:
                page++;
                picAdapter.addData((List<Pics>) event.data);
                picAdapter.loadMoreComplete();
                break;
            case BaseEvent.code_err:
                ToastUtils.showShort(activity, (String) event.data);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }


    private class PicAdapter extends MyBaseAdapter<Pics, BaseViewHolder> {
        public PicAdapter(@LayoutRes int layoutResId, @Nullable List<Pics> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Pics item) {
            GlideUtils.loadImageView(activity, item.getImgUrl(), (ImageView) helper.getView(R.id.ivPic));
        }
    }

}
