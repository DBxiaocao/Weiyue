package me.xiaocao.news.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * description: BaseListAdapter
 * author: lijun
 * date: 17/8/27 14:28
 */

public class BaseListAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public BaseListAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (onCallBackData != null) {
            onCallBackData.convertView(helper, item);
        }
    }

    public void setOnCallBackData(OnCallBackData<T> onCallBackData) {
        this.onCallBackData = onCallBackData;
    }

    private OnCallBackData onCallBackData;

    public interface OnCallBackData<T> {
        void convertView(BaseViewHolder helper, T item);
    }

    public int getItemPosition(T item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }
}
