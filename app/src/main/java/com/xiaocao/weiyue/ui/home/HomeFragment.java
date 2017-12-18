package com.xiaocao.weiyue.ui.home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import x.lib.ui.BaseFragment;

import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.dao.ChannelDao;
import com.xiaocao.weiyue.model.ChannelVo;
import x.lib.ui.BaseEvent;
import com.xiaocao.weiyue.model.event.ChannelEvent;
import com.xiaocao.weiyue.ui.adapter.ViewPagerAdapter;
import com.xiaocao.weiyue.ui.home.channel.ChannelActivity;
import com.xiaocao.weiyue.ui.home.news.NewsFragment;

import x.lib.utils.EventBusUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * className: HomeFragment
 * author: lijun
 * date: 17/6/30 10:11
 */

public class HomeFragment extends BaseFragment {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.ivAdd)
    ImageView ivAdd;
    @Bind(R.id.viewpager)
    ViewPager viewpager;


    @Override
    protected int setContentViewResId() {
        EventBusUtil.register(this);
        return R.layout.fragment_home;
    }

    @Override
    protected void initInstance() {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        fragments.clear();
        titles.clear();
        List<ChannelVo> dbList = ChannelDao.queryChannel(ChannelVo.TYPE_TOP);
        for (int i = 0; i < dbList.size(); i++) {
            fragments.add(NewsFragment.newInstance(dbList.get(i).getNewsId()));
            titles.add(dbList.get(i).getTitle());
        }

        ViewPagerAdapter videoAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewpager.setAdapter(videoAdapter);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void lazyLoad() {

    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onEvent(ChannelEvent event){
        if (event.code== BaseEvent.code_refresh){
            initInstance();
        }
    }

    @OnClick(R.id.ivAdd)
    public void onClick() {
        GoActivity(ChannelActivity.class);
    }

    @Override
    public void onDestroyView() {
        EventBusUtil.unregister(this);
        super.onDestroyView();
    }
}
