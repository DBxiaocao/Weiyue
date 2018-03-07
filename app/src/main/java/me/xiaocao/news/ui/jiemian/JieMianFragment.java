package me.xiaocao.news.ui.jiemian;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import me.xiaocao.news.R;
import me.xiaocao.news.ui.adapter.ViewPagerAdapter;
import me.xiaocao.news.ui.jiemian.channel.HomeListFragment;
import me.xiaocao.news.ui.jiemian.channel.OtherListFragment;
import x.lib.ui.BaseFragment;

/**
 * description: JieMianFragment
 * author: lijun
 * date: 18/1/4 19:37
 */

public class JieMianFragment extends BaseFragment {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_channel;
    }

    @Override
    protected void initInstance() {
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        List<Fragment> list = new ArrayList<>();
        list.add(HomeListFragment.newInstance());
        List<String> channel = Arrays.asList(getResources().getStringArray(R.array.jiemian_channel_id));
        for (String s : channel) {
            list.add(OtherListFragment.newInstance(s));
        }
        ViewPagerAdapter videoAdapter = new ViewPagerAdapter(getChildFragmentManager(), list, Arrays.asList(getResources().getStringArray(R.array.jiemian_channel)));
        viewpager.setAdapter(videoAdapter);
    }
}
