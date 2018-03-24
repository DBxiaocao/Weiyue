//package me.xiaocao.news.ui.pic;
//
//import android.os.Bundle;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.StaggeredGridLayoutManager;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.Bind;
//import me.xiaocao.news.R;
//import me.xiaocao.news.ui.adapter.BaseListAdapter;
//import me.xiaocao.news.app.Constants;
//import me.xiaocao.news.helper.PresenterFactory;
//import me.xiaocao.news.model.Pics;
//import me.xiaocao.news.model.event.PicEvent;
//import me.xiaocao.news.model.request.PicRequest;
//import me.xiaocao.news.presenter.IPicPresenter;
//import x.lib.ui.BaseEvent;
//import x.lib.ui.BaseFragment;
//import x.lib.utils.GlideUtils;
//
///**
// * description: PicFragment
// * author: lijun
// * date: 17/8/27 下午2:30
// */
//
//public class PicFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{
//    @Bind(R.id.recycler)
//    RecyclerView recycler;
//    @Bind(R.id.swipeRefresh)
//    SwipeRefreshLayout swipeRefresh;
//    private IPicPresenter iPicPresenter;
//    private BaseListAdapter<Pics> picAdapter;
//    private int page = 1;
//    private List<Pics> picses = new ArrayList<>();
//
//    @Override
//    protected int setContentViewResId() {
//        return R.layout.include_recycler;
//    }
//
//    @Override
//    protected void initInstance() {
//        iPicPresenter = PresenterFactory.getListPicPresenter();
//        picAdapter = new BaseListAdapter<>(R.layout.grid_item_pic, picses);
//        picAdapter.setOnCallBackData(new BaseListAdapter.OnCallBackData<Pics>() {
//            @Override
//            public void convertView(BaseViewHolder helper, Pics item) {
//                GlideUtils.loadImageView(activity, item.getImgUrl(), (ImageView) helper.getView(R.id.ivPic),R.mipmap.icon_no_picture);
//            }
//        });
//        recycler.setAdapter(picAdapter);
//        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
//        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
//        staggeredGridLayoutManager.invalidateSpanAssignments();
//        recycler.setLayoutManager(staggeredGridLayoutManager);
//        swipeRefresh.setOnRefreshListener(this);
//        picAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        picAdapter.setOnLoadMoreListener(this, recycler);
//        picAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Bundle bundle=new Bundle();
//                bundle.putString(Constants.PHOTO_URL, picAdapter.getItem(position).getImgUrl());
//                GoActivity(PhotoActivity.class,bundle);
//            }
//        });
//    }
//
//    @Override
//    protected boolean isRegisterEventBus() {
//        return true;
//    }
//
//    @Override
//    protected boolean isLazyLoad() {
//        return true;
//    }
//
//    @Override
//    protected void lazyLoad() {
//        if (picAdapter.getData().size() <= 0) {
//            onRefresh();
//        }
//    }
//
//    @Override
//    public void onRefresh() {
//        page = 1;
//        swipeRefresh.setRefreshing(true);
//        iPicPresenter.loadPic(new PicRequest().setPager_offset(page));
//    }
//
//    @Override
//    public void onLoadMoreRequested() {
//        if (picAdapter.getData().size() < 10) {
//            return;
//        }
//        iPicPresenter.loadPic(new PicRequest().setPager_offset(page));
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(PicEvent event) {
//        switch (event.code) {
//            case BaseEvent.code_success:
//                break;
//            case BaseEvent.code_load_err:
//                picAdapter.loadMoreEnd();
//                break;
//            case BaseEvent.code_refresh:
//                page++;
//                swipeRefresh.setRefreshing(false);
//                picAdapter.replaceData((List<Pics>) event.data);
//                break;
//            case BaseEvent.code_load:
//                page++;
//                picAdapter.addData((List<Pics>) event.data);
//                picAdapter.loadMoreComplete();
//                break;
//            case BaseEvent.code_err:
//                swipeRefresh.setRefreshing(false);
//                picAdapter.loadMoreFail();
//                showErrNetWork(recycler, (String) event.data);
//                break;
//        }
//    }
//}
