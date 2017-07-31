package com.xiaocao.weiyue.ui.video;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import x.lib.ui.BaseFragment;
import com.xiaocao.weiyue.Constants;
import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.ui.MainActivity;
import com.xiaocao.weiyue.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * className: VidioFragment
 * author: lijun
 * date: 17/6/30 10:18
 */

public class VideoFragment extends BaseFragment {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
//    @Bind(R.id.ivSearch)
//    ImageView ivSearch;
    @Bind(R.id.viewpager)
    ViewPager viewpager;


    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_video;
    }


    @Override
    protected void initInstance() {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles=new ArrayList<>();
        String[] videoName=getResources().getStringArray(R.array.video_name);
        String[] videoId=getResources().getStringArray(R.array.video_id);
        for (int i = 0; i < videoName.length; i++) {
            fragments.add(VideoListFragment.newInstance(videoId[i]));
            titles.add(videoName[i]);
        }
        ViewPagerAdapter videoAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewpager.setAdapter(videoAdapter);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void lazyLoad() {

    }
}
