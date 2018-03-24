package me.xiaocao.news.ui.video;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import java.util.Map;

import butterknife.Bind;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
import cn.jzvd.JZVideoPlayerStandard;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.xiaocao.news.app.Api;
import me.xiaocao.news.app.ApiService;
import me.xiaocao.news.app.Constants;
import me.xiaocao.news.R;
import me.xiaocao.news.ui.adapter.BaseListAdapter;
import me.xiaocao.news.model.Video;
import me.xiaocao.news.model.request.VideoRequest;
import okhttp3.ResponseBody;
import x.lib.http.retrofit.RetrofitUtil;
import x.lib.ui.BaseFragment;
import x.lib.utils.GlideUtils;
import x.lib.utils.JsonUtil;
import x.lib.utils.ToastUtils;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        limit = 0;
        mSwipeRefresh.setRefreshing(true);
        getData(new VideoRequest().setLimit(limit).setId(getArguments().getString(Constants.VIDEO_ID)));
    }

    @Override
    public void onLoadMoreRequested() {
        getData(new VideoRequest().setLimit(limit).setId(getArguments().getString(Constants.VIDEO_ID)));
    }

    private void getData(final VideoRequest request) {
        RetrofitUtil.getInstance()
                .retrofit(Api.VIDEO_HOST)
                .create(ApiService.class)
                .getVideoList(request.url(), request.params())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ResponseBody, List<Video>>() {
                    @Override
                    public List<Video> apply(@NonNull ResponseBody result) throws Exception {
                        Map<String, List<Video>> map = JsonUtil.getJsonMap(result.string());
                        return JsonUtil.jsonToList(map.get(request.id).toString(), Video.class);
                    }
                })
                .subscribe(new Consumer<List<Video>>() {
                    @Override
                    public void accept(List<Video> videos) throws Exception {
                        if (videos.isEmpty()) {
                            listAdapter.loadMoreFail();
                        } else {
                            limit += 20;
                            if (request.limit == 0) {
                                mSwipeRefresh.setRefreshing(false);
                                listAdapter.setNewData(videos);
                            } else{
                                listAdapter.addData(videos);
                                listAdapter.loadMoreComplete();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort(activity, throwable.getMessage());
                    }
                });
    }
}
