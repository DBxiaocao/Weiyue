package com.xiaocao.weiyue.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaocao.weiyue.utils.MyLoadMoreView;

import java.util.List;

/**
 * description: MyBaseAdapter
 * author: xiaocao
 * date: 17/7/23 15:45
 */

public abstract class MyBaseAdapter<T,K extends BaseViewHolder> extends BaseQuickAdapter<T,K> {
    public MyBaseAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        this.setLoadMoreView(new MyLoadMoreView());
    }
    protected int getItemPosition(T item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }
}
