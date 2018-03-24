//package me.xiaocao.news.ui.news;
//
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.Bind;
//import me.xiaocao.news.R;
//import me.xiaocao.news.ui.adapter.ViewPagerAdapter;
//import me.xiaocao.news.app.Api;
//import me.xiaocao.news.helper.PresenterFactory;
//import me.xiaocao.news.model.event.ChannelEvent;
//import me.xiaocao.news.model.request.NewsChannelRequest;
//import me.xiaocao.news.presenter.INewsPresenter;
//import x.lib.ui.BaseEvent;
//import x.lib.ui.BaseFragment;
//
///**
// * description: NewsChannelFragment
// * author: lijun
// * date: 17/8/27 14:13
// */
//
//public class NewsFragment extends BaseFragment {
//    @Bind(R.id.tabLayout)
//    TabLayout tabLayout;
//    @Bind(R.id.viewpager)
//    ViewPager viewpager;
//    private INewsPresenter mPresenter;
//
//    @Override
//    protected int setContentViewResId() {
//        return R.layout.fragment_channel;
//    }
//
//    @Override
//    protected void initInstance() {
//        mPresenter= PresenterFactory.getNewsPresenter();
//        mPresenter.getChannel(new NewsChannelRequest(Api.NEWS_KEY));
//        tabLayout.setupWithViewPager(viewpager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//    }
//
//    @Override
//    protected boolean isRegisterEventBus() {
//        return true;
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(ChannelEvent event){
//        switch (event.code){
//            case ChannelEvent.code_success:
//                List<Fragment> fragments = new ArrayList<>();
//                List<String> titles = (List<String>) event.data;
//                for (int i = 0; i < titles.size(); i++) {
//                    fragments.add(NewsListFragment.newInstance(titles.get(i)));
//                }
//                ViewPagerAdapter videoAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments, titles);
//                viewpager.setAdapter(videoAdapter);
//                break;
//            case BaseEvent.code_err:
//                break;
//        }
//    }
//}
