//package me.xiaocao.news.ui.read;
//
//import android.os.Bundle;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.RecyclerView;
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
//import me.xiaocao.news.model.Zhihu;
//import me.xiaocao.news.model.event.ZhihuEvent;
//import me.xiaocao.news.model.request.ZhihuListRequest;
//import me.xiaocao.news.presenter.IZhiHuPresenter;
//import x.lib.ui.BaseEvent;
//import x.lib.ui.BaseFragment;
//import x.lib.utils.GlideUtils;
//import x.lib.utils.StringUtils;
//
///**
// * description: ZhiHuFragment
// * author: lijun
// * date: 17/9/8 21:24
// */
//
//public class ZhiHuFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
//
//    @Bind(R.id.recycler)
//    RecyclerView recycler;
//    @Bind(R.id.swipeRefresh)
//    SwipeRefreshLayout swipeRefresh;
//    private BaseListAdapter<Zhihu> listAdapter;
//    private int timePage = 20170908;
//
//    public static ZhiHuFragment newInstance(int index) {
//        ZhiHuFragment fragment = new ZhiHuFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(Constants.KEY_READ_TYPE, index);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @Override
//    protected int setContentViewResId() {
//        return R.layout.fragment_list;
//    }
//
//    @Override
//    protected void initInstance() {
//        listAdapter = new BaseListAdapter<>(R.layout.list_item_news, new ArrayList<Zhihu>());
//        listAdapter.setOnCallBackData(new BaseListAdapter.OnCallBackData<Zhihu>() {
//            @Override
//            public void convertView(BaseViewHolder helper, Zhihu item) {
//                helper.setText(R.id.tvNewsTitle, item.getTitle());
//                ImageView image = helper.getView(R.id.ivNews);
//                if (item.getImages() != null && item.getImages().size() > 0) {
//                    if (!StringUtils.isEmpty(item.getImages().get(0))) {
//                        image.setVisibility(View.VISIBLE);
//                        GlideUtils.loadImageView(activity, item.getImages().get(0), image);
//                    } else {
//                        image.setVisibility(View.GONE);
//                    }
//                } else {
//                    image.setVisibility(View.GONE);
//                }
//
//            }
//        });
//        recycler.setAdapter(listAdapter);
//        swipeRefresh.setOnRefreshListener(this);
//        listAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        listAdapter.setPreLoadNumber(1);
//        listAdapter.setOnLoadMoreListener(this, recycler);
//        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Bundle bundle=new Bundle();
//                bundle.putString(Constants.KEY_READ_TITLE,listAdapter.getItem(position).getTitle());
//                bundle.putInt(Constants.KEY_READ_TYPE,0);
//                bundle.putString(Constants.KEY_READ_URL,listAdapter.getItem(position).getId()+"");
//                GoActivity(ReadDetailActivity.class,bundle);
//            }
//        });
//    }
//
//    @Override
//    protected boolean isLazyLoad() {
//        return true;
//    }
//
//    @Override
//    protected void lazyLoad() {
//        if (listAdapter.getData().size() <= 0)
//            onRefresh();
//    }
//
//    @Override
//    protected boolean isRegisterEventBus() {
//        return true;
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(ZhihuEvent event) {
//        switch (event.code) {
//            case BaseEvent.code_err:
//                swipeRefresh.setRefreshing(false);
//                listAdapter.loadMoreFail();
//                showErrNetWork(recycler, (String) event.data);
//                break;
//            case BaseEvent.code_refresh:
//                swipeRefresh.setRefreshing(false);
//                listAdapter.replaceData((List<Zhihu>) event.data);
//                break;
//            case BaseEvent.code_load:
//                listAdapter.addData((List<Zhihu>) event.data);
//                listAdapter.loadMoreComplete();
//                timePage--;
//                break;
//        }
//    }
//
//    @Override
//    public void onRefresh() {
//        timePage = 20170908;
//        swipeRefresh.setRefreshing(true);
//        mPresenter.getZhiHuList(new ZhihuListRequest().setToDay(true));
//    }
//
//    @Override
//    public void onLoadMoreRequested() {
//        mPresenter.getZhiHuList(new ZhihuListRequest().setToDay(false).setPage(timePage));
//    }
//}
