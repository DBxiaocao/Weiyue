package com.xiaocao.weiyue.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * description: CommAdapter
 * author: lijun
 * date: 17/8/18 12:01
 */

public class CommAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder>{
    public CommAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (onCallBackData!=null){
            onCallBackData.convertView(helper,item);
        }
    }

    public void setOnCallBackData(OnCallBackData<T> onCallBackData) {
        this.onCallBackData = onCallBackData;
    }

    private OnCallBackData onCallBackData;

    public interface OnCallBackData<T>{
        void convertView(BaseViewHolder helper, T item);
    }
}
