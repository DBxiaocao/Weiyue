package com.xiaocao.weiyue.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.dao.CollectionDao;
import com.xiaocao.weiyue.model.CollectionVo;
import com.xiaocao.weiyue.model.Video;
import com.xiaocao.weiyue.utils.IntentUtil;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import x.lib.utils.GlideUtils;
import x.lib.utils.ToastUtils;

/**
 * description: VideoAdapter
 * author: xiaocao
 * date: 17/7/28 11:40
 */

public class VideoAdapter extends MyBaseAdapter<Video, BaseViewHolder> {

    public VideoAdapter(@LayoutRes int layoutResId, @Nullable List<Video> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, final Video item) {
        JCVideoPlayerStandard jcVideoPlayerStandard = helper.getView(R.id.vdPlayer);
        jcVideoPlayerStandard.setUp(item.getMp4_url(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, item.getTitle());
        GlideUtils.loadImageView(mContext, item.getCover(), jcVideoPlayerStandard.thumbImageView);
        helper.setText(R.id.tvUserName, item.getTopicName());
        GlideUtils.loadImageView(mContext, item.getTopicImg(), (ImageView) helper.getView(R.id.ivAvatar));
        ImageView ivShare = helper.getView(R.id.ivShare);
        ivShare.setVisibility(View.VISIBLE);
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.shareText((AppCompatActivity) mContext, item.getTitle(), item.getMp4_url());
            }
        });
        final ImageView ivCollection = helper.getView(R.id.ivCollection);
        final CollectionVo dbVo = CollectionDao.queryImgUrl(item.getCover());
        if (null != dbVo && dbVo.getImgUrl().equals(item.getCover())) {
            ivCollection.setImageResource(R.mipmap.icon_collection_true);
        } else {
            ivCollection.setImageResource(R.mipmap.icon_collection_false);
            ivCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != dbVo && dbVo.getImgUrl().equals(item.getCover())) {
                        CollectionDao.deleteChannel(dbVo.getId());
                        ToastUtils.showShort(mContext, "已取消收藏");
                        ivCollection.setImageResource(R.mipmap.icon_collection_false);
                    } else {
                        CollectionVo vo = new CollectionVo();
                        vo.setType(CollectionVo.TYPE_Video);
                        vo.setImgUrl(item.getCover());
                        vo.setTitle(item.getTitle());
                        vo.setNewsId(item.getReplyid());
                        vo.setId(CollectionDao.queryAll().size() + 1);
                        vo.setUrl(item.getMp4_url());
                        vo.setAvatar(item.getTopicImg());
                        vo.setUser(item.getTopicName());
                        CollectionDao.insert(vo);
                        ToastUtils.showShort(mContext, "已添加收藏");
                        ivCollection.setImageResource(R.mipmap.icon_collection_true);
                    }
//                        notifyItemChanged(getItemPosition(item));
                }
            });
        }
    }
}