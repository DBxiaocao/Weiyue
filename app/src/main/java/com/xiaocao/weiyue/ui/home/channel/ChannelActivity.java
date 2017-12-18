package com.xiaocao.weiyue.ui.home.channel;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.dao.ChannelDao;
import com.xiaocao.weiyue.model.ChannelVo;

import butterknife.BindColor;
import x.lib.ui.BaseEvent;

import com.xiaocao.weiyue.model.event.ChannelEvent;
import com.xiaocao.weiyue.ui.adapter.MyBaseAdapter;

import x.lib.ui.BaseMvpActivity;
import x.lib.utils.EventBusUtil;

import x.lib.ui.BaseActivity;
import x.lib.ui.TitleView;
import x.lib.utils.ButtonClickUtils;

//import com.xiaocao.weiyue.R;
//import com.xiaocao.weiyue.dao.ChannelDao;
//import com.xiaocao.weiyue.model.TestMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * className: ChannelActivity
 * author: lijun
 * date: 17/7/4 16:46
 */

public class ChannelActivity extends BaseMvpActivity<ChannelPresenter> implements ChannelContract.IView {
    private static final String TAG = "ChannelActivity";
    @Bind(R.id.checkRecycler)
    RecyclerView checkRecycler;
    @Bind(R.id.addRecycler)
    RecyclerView addRecycler;
    @BindColor(R.color.tab_unchecked)
    int chenckColor;
    @BindColor(R.color.tab_checked)
    int unColor;

    private List<ChannelVo> checkStrs = new ArrayList<>();
    private List<ChannelVo> addStrs = new ArrayList<>();
    private CheckAdapter checkAdapter;
    private AddAdapter addAdapter;


    @Override
    protected int setContentViewResId() {
        return R.layout.activity_channel;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setBack(activity);
        title.setTitleText("频道管理");
    }

    @Override
    protected void initInstance() {
        checkRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        addRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        checkAdapter = new CheckAdapter(R.layout.grid_item_tabs, checkStrs);
        checkRecycler.setAdapter(checkAdapter);
        addAdapter = new AddAdapter(R.layout.grid_item_tabs, addStrs);
        addRecycler.setAdapter(addAdapter);
        mPresenter.getTopList();
        mPresenter.getBottom();
    }

    @Override
    public void onErrMsg(String errMsg) {

    }

    @Override
    public void setTopList(List<ChannelVo> list) {
        checkStrs = list;
        checkAdapter.setNewData(list);
    }

    @Override
    public void setBottom(List<ChannelVo> list) {
        addStrs = list;
        addAdapter.setNewData(list);
    }

    private class CheckAdapter extends MyBaseAdapter<ChannelVo, BaseViewHolder> {
        public CheckAdapter(@LayoutRes int layoutResId, @Nullable List<ChannelVo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ChannelVo item) {
            TextView tvTab = helper.getView(R.id.tvTab);
            tvTab.setText(item.getTitle());
            if (getItemPosition(item) > 4) {
                tvTab.setTextColor(chenckColor);
            } else {
                tvTab.setTextColor(unColor);
            }
            tvTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ButtonClickUtils.isFastDoubleClick()) {
                        return;
                    }
                    if (getItemPosition(item) > 4) {
                        item.setType(ChannelVo.TYPE_BOTTOM);
                        remove(getItemPosition(item));
                        addAdapter.addData(0, item);
                        ChannelDao.updateChannel(item);
                        EventBusUtil.sendEvent(new ChannelEvent(BaseEvent.code_refresh, null, null));
                    }
                }
            });
        }
    }

    private class AddAdapter extends MyBaseAdapter<ChannelVo, BaseViewHolder> {
        public AddAdapter(@LayoutRes int layoutResId, @Nullable List<ChannelVo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ChannelVo item) {
            TextView tvTab = helper.getView(R.id.tvTab);
            tvTab.setText(item.getTitle());
            tvTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ButtonClickUtils.isFastDoubleClick()) {
                        return;
                    }
                    item.setType(ChannelVo.TYPE_TOP);
                    ChannelDao.updateChannel(item);
                    remove(getItemPosition(item));
                    checkAdapter.addData(item);
                    EventBusUtil.sendEvent(new ChannelEvent(BaseEvent.code_refresh, null, null));
                }
            });
        }

    }
}
