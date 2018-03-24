//package me.xiaocao.news.ui.read;
//
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import butterknife.Bind;
//import me.xiaocao.news.R;
//import me.xiaocao.news.ui.adapter.ViewPagerAdapter;
//import x.lib.ui.BaseFragment;
//
///**
// * description: ReadFragment
// * author: lijun
// * date: 17/9/8 21:23
// */
//
//public class ReadFragment extends BaseFragment {
//    @Bind(R.id.tabLayout)
//    TabLayout tabLayout;
//    @Bind(R.id.viewpager)
//    ViewPager viewpager;
//
//    @Override
//    protected int setContentViewResId() {
//        return R.layout.fragment_channel;
//    }
//
//
//    @Override
//    protected void initInstance() {
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(ZhiHuFragment.newInstance(0));
//        fragments.add(GuokrFragment.newInstance(1));
//        ViewPagerAdapter videoAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments, Arrays.asList(getResources().getStringArray(R.array.read_title)));
//        viewpager.setAdapter(videoAdapter);
//        tabLayout.setupWithViewPager(viewpager);
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//    }
//}
