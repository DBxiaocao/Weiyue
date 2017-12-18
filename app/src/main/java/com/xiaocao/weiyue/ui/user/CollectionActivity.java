package com.xiaocao.weiyue.ui.user;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaocao.weiyue.Constants;
import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.dao.CollectionDao;
import com.xiaocao.weiyue.model.CollectionVo;
import com.xiaocao.weiyue.ui.home.detail.NewsDetailActivity;

import java.util.List;

import butterknife.Bind;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import x.lib.ui.BaseActivity;
import x.lib.ui.TitleView;
import x.lib.utils.GlideUtils;
import x.lib.utils.ToastUtils;

public class CollectionActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    //    private MyAdapter myAdapter;
    private CollectionAdapter adapter;
    private int isNews = 0;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setBack(activity);
        title.setTitleText("我的收藏");
    }

    @Override
    protected void initInstance() {
        List<CollectionVo> dbs = CollectionDao.queryAll();
        adapter = new CollectionAdapter(dbs);
        recycler.setAdapter(adapter);
        swipeRefresh.setOnRefreshListener(this);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter madapter, View view, int position) {
                switch (adapter.getItem(position).getType()) {
                    case CollectionVo.TYPE_News:
                        Bundle bundle=new Bundle();
                        bundle.putString(Constants.NEWS_Title, adapter.getItem(position).getTitle());
                        bundle .putString(Constants.NEWS_Img, adapter.getItem(position).getImgUrl())  ;
                        bundle  .putString(Constants.NEWS_HTML, adapter.getItem(position).getUrl())    ;
                        bundle    .putString(Constants.NEWS_ID, adapter.getItem(position).getNewsId())  ;
                        GoActivity(NewsDetailActivity.class,bundle);
                        break;
                    case CollectionVo.TYPE_Video:
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuNews:
                isNews = 1;
                onRefresh();
                break;
            case R.id.menuVideo:
                isNews = 2;
                onRefresh();
                break;
        }
        return true;
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(true);
        if (isNews == 0) {
            adapter.setNewData(CollectionDao.queryAll());
        } else if (isNews == 1) {
            adapter.setNewData(CollectionDao.query(CollectionVo.TYPE_News));
        } else {
            adapter.setNewData(CollectionDao.query(CollectionVo.TYPE_Video));
        }
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }


    private class CollectionAdapter extends BaseMultiItemQuickAdapter<CollectionVo, BaseViewHolder> {

        public CollectionAdapter(List<CollectionVo> data) {
            super(data);
            addItemType(CollectionVo.TYPE_News, R.layout.list_item_news);
            addItemType(CollectionVo.TYPE_Video, R.layout.list_item_video);
        }

        @Override
        protected void convert(BaseViewHolder helper, final CollectionVo item) {
            switch (item.getType()) {
                case CollectionVo.TYPE_News:
                    helper.setText(R.id.tvNewsTitle, item.getTitle());
                    GlideUtils.loadImageView(activity, item.getImgUrl(), (ImageView) helper.getView(R.id.ivNews));
                    break;
                case CollectionVo.TYPE_Video:
                    JCVideoPlayerStandard jcVideoPlayerStandard = helper.getView(R.id.vdPlayer);
                    jcVideoPlayerStandard.setUp(item.getUrl(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, item.getTitle());
                    GlideUtils.loadImageView(activity, item.getImgUrl(), jcVideoPlayerStandard.thumbImageView);
                    helper.setText(R.id.tvUserName, item.getUser());
                    GlideUtils.loadImageView(activity, item.getAvatar(), (ImageView) helper.getView(R.id.ivAvatar));
                    ImageView ivCollection = helper.getView(R.id.ivCollection);
                    final CollectionVo dbVo = CollectionDao.queryImgUrl(item.getImgUrl());
                    ivCollection.setImageResource(R.mipmap.icon_collection_true);
                    ivCollection.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (null != dbVo && dbVo.getImgUrl().equals(item.getImgUrl())) {
                                CollectionDao.deleteChannel(dbVo.getId());
                                ToastUtils.showShort(activity, "已取消收藏");
                                remove(getItemPosition(item));
                            }
                        }
                    });
                    break;

            }
        }
        private int getItemPosition(CollectionVo item) {
            return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
        }
    }

}
