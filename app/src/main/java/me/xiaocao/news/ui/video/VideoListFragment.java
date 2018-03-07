package me.xiaocao.news.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
import cn.jzvd.JZVideoPlayerStandard;
import me.xiaocao.news.app.Constants;
import me.xiaocao.news.R;
import me.xiaocao.news.ui.adapter.BaseListAdapter;
import me.xiaocao.news.helper.PresenterFactory;
import me.xiaocao.news.model.Video;
import me.xiaocao.news.model.request.VideoRequest;
import me.xiaocao.news.presenter.IVideoPresenter;
import x.lib.ui.BaseEvent;
import x.lib.ui.BaseFragment;
import x.lib.utils.GlideUtils;

/** 
 * description: VideoListFragment
 * author: lijun
 * date: 17/8/27 下午2:57
*/

public class VideoListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    private IVideoPresenter mPresenter;

    private List<Video> videoList = new ArrayList<>();
    private BaseListAdapter<Video> listAdapter;
    private int limit;

    public static VideoListFragment newInstance(String index) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.VIDEO_ID, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.include_recycler;
    }

    @Override
    protected void initInstance() {
        listAdapter = new BaseListAdapter<>(R.layout.list_item_video, videoList);
        listAdapter.setOnCallBackData(new BaseListAdapter.OnCallBackData<Video>() {
            @Override
            public void convertView(BaseViewHolder helper, Video item) {
                JZVideoPlayerStandard jcVideoPlayerStandard = helper.getView(R.id.vdPlayer);
                jcVideoPlayerStandard.setUp(item.getMp4_url(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, item.getTitle());
                GlideUtils.loadImageView(activity, item.getCover(), jcVideoPlayerStandard.thumbImageView);
                helper.setText(R.id.tvUserName, item.getTopicName());
                GlideUtils.loadImageView(activity, item.getTopicImg(), (ImageView) helper.getView(R.id.ivAvatar));
            }
        });
        mRecycler.setAdapter(listAdapter);
        mSwipeRefresh.setOnRefreshListener(this);
        mPresenter = PresenterFactory.getVideoListPresenter();
        listAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        listAdapter.setPreLoadNumber(1);
        listAdapter.setOnLoadMoreListener(this, mRecycler);
        mRecycler.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (JZVideoPlayerManager.getFirstFloor() != null) {
                    JZVideoPlayer videoPlayer = JZVideoPlayerManager.getFirstFloor();
                    if (((ViewGroup) view).indexOfChild(videoPlayer) != -1 && videoPlayer.currentState == JZVideoPlayer.CURRENT_STATE_PLAYING) {
                        JZVideoPlayer.releaseAllVideos();
                    }
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        JZVideoPlayer.releaseAllVideos();
        if (listAdapter.getData().size() <= 0) {
            onRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        if (event.id == getArguments().getString(Constants.VIDEO_ID)) {
            Logger.e("打印了数据" + event.code);
            switch (event.code) {
                case BaseEvent.code_success:
                    break;
                case BaseEvent.code_refresh:
                    limit += 20;
                    mSwipeRefresh.setRefreshing(false);
                    listAdapter.getData().clear();
                    listAdapter.addData((List<Video>) event.data);
                    break;
                case BaseEvent.code_load:
                    limit += 20;
                    listAdapter.addData((List<Video>) event.data);
                    listAdapter.loadMoreComplete();
                    break;
                case BaseEvent.code_err:
                    mSwipeRefresh.setRefreshing(false);
                    listAdapter.loadMoreFail();
                    showErrNetWork(mRecycler, (String) event.data);
                    break;
            }
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
}
