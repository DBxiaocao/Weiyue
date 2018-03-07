package me.xiaocao.news.ui.wangyi;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.xiaocao.news.R;
import me.xiaocao.news.ui.adapter.ViewPagerAdapter;
import me.xiaocao.news.model.event.ChannelEvent;
import x.lib.ui.BaseEvent;
import x.lib.ui.BaseFragment;
import x.lib.utils.EventBusUtil;

/**
 * className: WngyiFragment
 * author: lijun
 * date: 17/6/30 10:11
 */

public class WangyiFragment extends BaseFragment {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
//    @Bind(R.id.ivAdd)
//    ImageView ivAdd;
    @Bind(R.id.viewpager)
    ViewPager viewpager;


    @Override
    protected int setContentViewResId() {
        EventBusUtil.register(this);
        return R.layout.fragment_channel;
    }

    @Override
    protected void initInstance() {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        fragments.clear();
        titles.clear();
        for (int i = 0; i < getResources().getStringArray(R.array.wy_channel_id).length; i++) {
            fragments.add(WangyiListFragment.newInstance(getResources().getStringArray(R.array.wy_channel_id)[i]));
            titles.add(getResources().getStringArray(R.array.wy_channel_name)[i]);
        }

        ViewPagerAdapter videoAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewpager.setAdapter(videoAdapter);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onEvent(ChannelEvent event){
        if (event.code== BaseEvent.code_refresh){
            initInstance();
        }
    }

//    @OnClick(R.id.ivAdd)
//    public void onClick() {
////        GoActivity(ChannelActivity.class);
//    }

    @Override
    public void onDestroyView() {
        EventBusUtil.unregister(this);
        super.onDestroyView();
    }
}
