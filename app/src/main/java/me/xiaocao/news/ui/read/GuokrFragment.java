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
//import java.util.Collection;
//
//import butterknife.Bind;
//import me.xiaocao.news.R;
//import me.xiaocao.news.ui.adapter.BaseListAdapter;
//import me.xiaocao.news.app.Constants;
//import me.xiaocao.news.helper.PresenterFactory;
//import me.xiaocao.news.model.Guokr;
//import me.xiaocao.news.model.event.GuokrListEvent;
//import me.xiaocao.news.model.request.GuokrListRequest;
//import me.xiaocao.news.presenter.IGuokrPresenter;
//import x.lib.ui.BaseEvent;
//import x.lib.ui.BaseFragment;
//import x.lib.utils.GlideUtils;
//import x.lib.utils.StringUtils;
//
///**
// * description: GuokrFragment
// * author: lijun
// * date: 17/9/8 21:24
// */
//
//public class GuokrFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
//
//    private static final String retrieveType = "by_subject";
//    private static final String channelType = "";
//    @Bind(R.id.recycler)
//    RecyclerView recycler;
//    @Bind(R.id.swipeRefresh)
//    SwipeRefreshLayout swipeRefresh;
//    private IGuokrPresenter mPresenter;
//    private BaseListAdapter<Guokr> listAdapter;
//    private int page;
//
//    public static GuokrFragment newInstance(int index) {
//        GuokrFragment fragment = new GuokrFragment();
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
//        mPresenter = PresenterFactory.getGuokrPresenter();
//        listAdapter = new BaseListAdapter<>(R.layout.list_item_news, new ArrayList<Guokr>());
//        listAdapter.setOnCallBackData(new BaseListAdapter.OnCallBackData<Guokr>() {
//            @Override
//            public void convertView(BaseViewHolder helper, Guokr item) {
//                helper.setText(R.id.tvNewsTitle, item.getTitle());
//                ImageView ivNews=helper.getView(R.id.ivNews);
//                if (StringUtils.isEmpty(item.getSmall_image())){
//                    ivNews.setVisibility(View.GONE);
//                }else {
//                    ivNews.setVisibility(View.VISIBLE);
//                    GlideUtils.loadImageView(activity, item.getSmall_image(), ivNews);
//                }
//            }
//        });
//        recycler.setAdapter(listAdapter);
//        listAdapter.setPreLoadNumber(1);
//        swipeRefresh.setOnRefreshListener(this);
//        listAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        listAdapter.setOnLoadMoreListener(this, recycler);
//        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Bundle bundle=new Bundle();
//                bundle.putString(Constants.KEY_READ_TITLE,listAdapter.getItem(position).getTitle());
//                bundle.putInt(Constants.KEY_READ_TYPE,1);
//                bundle.putString(Constants.KEY_READ_URL,listAdapter.getItem(position).getResource_url());
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
//    @Override
//    public void onRefresh() {
//        swipeRefresh.setRefreshing(true);
//        page = 0;
//        mPresenter.getGuokrList(new GuokrListRequest().setOffset(page).setRetrieve_type(retrieveType).setChannel_key(channelType));
//    }
//
//    @Override
//    public void onLoadMoreRequested() {
//        mPresenter.getGuokrList(new GuokrListRequest().setOffset(page).setRetrieve_type(retrieveType).setChannel_key(channelType));
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(GuokrListEvent event) {
//        switch (event.code) {
//            case BaseEvent.code_err:
//                swipeRefresh.setRefreshing(false);
//                listAdapter.loadMoreFail();
//                showErrNetWork(recycler, (String) event.data);
//                break;
//            case BaseEvent.code_refresh:
//                page++;
//                swipeRefresh.setRefreshing(false);
//                listAdapter.replaceData((Collection<? extends Guokr>) event.data);
//                break;
//            case BaseEvent.code_load:
//                page++;
//                listAdapter.addData((Collection<? extends Guokr>) event.data);
//                listAdapter.loadMoreComplete();
//                break;
//        }
//    }
//}
