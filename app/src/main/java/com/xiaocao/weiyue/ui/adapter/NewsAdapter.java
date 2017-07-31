package com.xiaocao.weiyue.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.model.News;

import java.util.List;

import x.lib.utils.GlideUtils;

/**
 * description: NewsAdapter
 * author: xiaocao
 * date: 17/7/23 15:45
 */

public class NewsAdapter extends MyBaseAdapter<News,BaseViewHolder>{

    public NewsAdapter(@LayoutRes int layoutResId, @Nullable List<News> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        helper.setText(R.id.tvNewsTitle, item.getTitle());
        helper.setText(R.id.tvNewsSource, item.getSource());
        helper.setText(R.id.tvNewsTime, item.getPtime());
        GlideUtils.loadImageView(mContext, item.getImgsrc(), (ImageView) helper.getView(R.id.ivNews));

    }
}
