package com.xiaocao.weiyue.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import x.http.HttpUtils;
import x.http.result.ResponseResult;
import x.http.util.JsonUtil;
import x.http.util.RequestUtil;
import x.lib.handler.FragmentHandler;
import x.lib.ui.BaseActivity;

import com.xiaocao.weiyue.Api;
import com.xiaocao.weiyue.Constants;
import com.xiaocao.weiyue.MyApp;
import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.model.News;
import com.xiaocao.weiyue.model.request.TestRequest;
import com.xiaocao.weiyue.ui.behavior.BottomNavigationViewHelper;
import com.xiaocao.weiyue.ui.home.HomeFragment;
import com.xiaocao.weiyue.ui.picture.PictureFragment;
import com.xiaocao.weiyue.ui.user.UserFragment;
import com.xiaocao.weiyue.ui.video.VideoFragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import x.lib.ui.BaseEvent;
import x.lib.ui.TitleView;
import x.lib.utils.EventBusUtil;
import x.lib.utils.FragmentUtils;

/**
 * description: MainActivity
 * author: xiaocao
 * date: 17/7/22 下午2:47
 */
public class MainActivity extends BaseActivity {
    //CONSARH30001875N
    @Bind(R.id.flContent)
    FrameLayout flContent;
    @Bind(R.id.navigationView)
    BottomNavigationView navigationView;
    private MenuItem prevMenuItem;
    private FragmentHandler fragmentHandler;
    private TitleView title;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        title = new TitleView(activity, findViewById(R.id.toolBar));
        title.setTitleText("文章");
    }

    @Override
    protected void initInstance() {
        initThreadHelper();
//        fragmentHandler = new FragmentHandler(activity, getSupportFragmentManager());
        if (null != flContent)
            FragmentUtils.replaceFragment(getSupportFragmentManager(),R.id.flContent,new HomeFragment());
//            fragmentHandler.addOrShowFragment(flContent, Constants.HOME_FRAGMENT, new HomeFragment());
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemHome:
                        title.setTitleText("文章");
                        JCVideoPlayer.releaseAllVideos();
//                        fragmentHandler.addOrShowFragment(flContent, Constants.HOME_FRAGMENT, new HomeFragment());
                        FragmentUtils.replaceFragment(getSupportFragmentManager(),R.id.flContent,new HomeFragment());
                        break;
                    case R.id.itemVideo:
                        title.setTitleText("视频");
                        JCVideoPlayer.releaseAllVideos();
//                        fragmentHandler.addOrShowFragment(flContent, Constants.VIDEO_FRAGMENT, new VideoFragment());
                        FragmentUtils.replaceFragment(getSupportFragmentManager(),R.id.flContent,new VideoFragment());
                        break;
                    case R.id.itemPicture:
                        title.setTitleText("美图");
                        JCVideoPlayer.releaseAllVideos();
//                        fragmentHandler.addOrShowFragment(flContent, Constants.PICTURE_FRAGMENT, new PictureFragment());
                        FragmentUtils.replaceFragment(getSupportFragmentManager(),R.id.flContent,new PictureFragment());
                        break;
                    case R.id.itemUser:
                        title.setTitleText("");
                        JCVideoPlayer.releaseAllVideos();
//                        fragmentHandler.addOrShowFragment(flContent, Constants.USER_FRAGMENT, new UserFragment());
                        FragmentUtils.replaceFragment(getSupportFragmentManager(),R.id.flContent,new UserFragment());
                        break;
                }
                return true;
            }
        });
        if (prevMenuItem != null)
            prevMenuItem.setChecked(false);
        else
            prevMenuItem = navigationView.getMenu().getItem(0).setCheckable(true);
        BottomNavigationViewHelper.disableShiftMode(navigationView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        ((MyApp) activity.getApplication()).activityManagement.clearAllActivity();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        fragmentHandler.destroyView();
        super.onDestroy();
    }

}
