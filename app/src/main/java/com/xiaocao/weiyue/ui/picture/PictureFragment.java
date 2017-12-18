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
import x.lib.ui.BaseMvpFragment;
import x.lib.utils.GlideUtils;
import x.lib.utils.ToastUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.xiaocao.weiyue.Constants;
import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.model.Pics;
import x.lib.ui.BaseEvent;
import com.xiaocao.weiyue.model.event.PicEvent;
import com.xiaocao.weiyue.model.request.PicRequest;
import com.xiaocao.weiyue.presenter.IPicPresenter;
import com.xiaocao.weiyue.ui.adapter.CommAdapter;
import com.xiaocao.weiyue.ui.adapter.MyBaseAdapter;
import x.lib.utils.EventBusUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * className: Picturefragment
 * author: lijun
 * date: 17/6/30 10:20
 */

public class PictureFragment extends BaseMvpFragment<PicPresenterImpl> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,PicContract.IView {
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private CommAdapter<Pics> picAdapter;
    private int page = 1;
    private List<Pics> picses = new ArrayList<>();

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_picture;
    }

    @Override
    protected void initInstance() {
        picAdapter=new CommAdapter<>(R.layout.grid_item_pic,picses);
        picAdapter.setOnCallBackData(new CommAdapter.OnCallBackData<Pics>() {
            @Override
            public void convertView(BaseViewHolder helper, Pics item) {
                GlideUtils.loadImageView(activity, item.getImgUrl(), (ImageView) helper.getView(R.id.ivPic));
            }
        });
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
    public void onRefresh() {
        page = 1;
        swipeRefresh.setRefreshing(true);
        mPresenter.getPicList(new PicRequest().setPager_offset(page));
    }

    @Override
    public void onLoadMoreRequested() {
        if (picAdapter.getData().size() < 10) {
            return;
        }
        mPresenter.getPicList(new PicRequest().setPager_offset(page));
    }



    @Override
    public void onErrMsg(String errMsg) {
        ToastUtils.showShort(activity,errMsg);
    }

    @Override
    public void picReFresh(List<Pics> list) {
        page++;
        swipeRefresh.setRefreshing(false);
        picAdapter.setNewData(list);
    }

    @Override
    public void picLoad(List<Pics> list) {
        page++;
        picAdapter.addData(list);
        picAdapter.loadMoreComplete();
    }

}
