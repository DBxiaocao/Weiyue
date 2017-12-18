package com.xiaocao.weiyue.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import x.lib.ui.BaseFragment;
import x.lib.ui.BaseMvpFragment;
import x.lib.utils.GlideUtils;
import x.lib.utils.LogUtil;
import x.lib.utils.ToastUtils;

import com.xiaocao.weiyue.Constants;
import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.dao.CollectionDao;
import com.xiaocao.weiyue.helper.PresenterFactory;
import com.xiaocao.weiyue.model.CollectionVo;
import com.xiaocao.weiyue.model.Video;

import x.lib.ui.BaseEvent;

import com.xiaocao.weiyue.model.request.VideoRequest;
import com.xiaocao.weiyue.presenter.IVideoPresenter;
import com.xiaocao.weiyue.ui.adapter.CommAdapter;
import com.xiaocao.weiyue.ui.adapter.VideoAdapter;
import com.xiaocao.weiyue.utils.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * className: VideoListFragment
 * author: lijun
 * date: 17/6/30 16:56
 */

public class VideoListFragment extends BaseMvpFragment<VideoListPresenter> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, VideoListContract.IView {

    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    private CommAdapter<Video> listAdapter;

    private List<Video> videoList = new ArrayList<>();
    private int limit;

    public static VideoListFragment newInstance(String index) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.VIDEO_ID, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void initInstance() {
        listAdapter = new CommAdapter<>(R.layout.list_item_video, videoList);
        listAdapter.setOnCallBackData(new CommAdapter.OnCallBackData<Video>() {
            @Override
            public void convertView(BaseViewHolder helper, final Video item) {
                JCVideoPlayerStandard jcVideoPlayerStandard = helper.getView(R.id.vdPlayer);
                jcVideoPlayerStandard.setUp(item.getMp4_url(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, item.getTitle());
                GlideUtils.loadImageView(activity, item.getCover(), jcVideoPlayerStandard.thumbImageView);
                helper.setText(R.id.tvUserName, item.getTopicName());
                GlideUtils.loadImageView(activity, item.getTopicImg(), (ImageView) helper.getView(R.id.ivAvatar));
                ImageView ivShare = helper.getView(R.id.ivShare);
                ivShare.setVisibility(View.VISIBLE);
                ivShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IntentUtil.shareText(activity, item.getTitle(), item.getMp4_url());
                    }
                });
                final ImageView ivCollection = helper.getView(R.id.ivCollection);
                LogUtil.info(item.getCover());
//                final CollectionVo dbVo = CollectionDao.queryImgUrl(item.getCover());
//                if (null != dbVo && dbVo.getImgUrl().equals(item.getCover())) {
//                    ivCollection.setImageResource(R.mipmap.icon_collection_true);
//                } else {
//                    ivCollection.setImageResource(R.mipmap.icon_collection_false);
//                    ivCollection.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (null != dbVo && dbVo.getImgUrl().equals(item.getCover())) {
//                                CollectionDao.deleteChannel(dbVo.getId());
//                                ToastUtils.showShort(activity, "已取消收藏");
//                                ivCollection.setImageResource(R.mipmap.icon_collection_false);
//                            } else {
//                                CollectionVo vo = new CollectionVo();
//                                vo.setType(CollectionVo.TYPE_Video);
//                                vo.setImgUrl(item.getCover());
//                                vo.setTitle(item.getTitle());
//                                vo.setNewsId(item.getReplyid());
//                                vo.setId(CollectionDao.queryAll().size() + 1);
//                                vo.setUrl(item.getMp4_url());
//                                vo.setAvatar(item.getTopicImg());
//                                vo.setUser(item.getTopicName());
//                                CollectionDao.insert(vo);
//                                ToastUtils.showShort(activity, "已添加收藏");
//                                ivCollection.setImageResource(R.mipmap.icon_collection_true);
//                            }
//                        }
//                    });
//                }
            }
        });
        mRecycler.setAdapter(listAdapter);
        mSwipeRefresh.setOnRefreshListener(this);
        listAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        listAdapter.setPreLoadNumber(1);
        listAdapter.setOnLoadMoreListener(this, mRecycler);
        mRecycler.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (JCVideoPlayerManager.getFirstFloor() != null) {
                    JCVideoPlayer videoPlayer = JCVideoPlayerManager.getFirstFloor();
                    if (((ViewGroup) view).indexOfChild(videoPlayer) != -1 && videoPlayer.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                        JCVideoPlayer.releaseAllVideos();
                    }
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        JCVideoPlayer.releaseAllVideos();
        if (listAdapter.getData().size() <= 0) {
            onRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        limit = 0;
        mSwipeRefresh.setRefreshing(true);
        mPresenter.getVideoList(new VideoRequest().setLimit(limit).setId(getArguments().getString(Constants.VIDEO_ID)));
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getVideoList(new VideoRequest().setLimit(limit).setId(getArguments().getString(Constants.VIDEO_ID)));
    }

    @Override
    public void onErrMsg(String errMsg) {
        ToastUtils.showShort(activity, errMsg);
    }

    @Override
    public void videoRefresh(List<Video> list) {
        limit += 20;
        mSwipeRefresh.setRefreshing(false);
        listAdapter.setNewData(list);
    }

    @Override
    public void videoLoad(List<Video> list) {
        limit += 20;
        listAdapter.addData(list);
        listAdapter.loadMoreComplete();
    }
}
