package me.xiaocao.news;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import butterknife.Bind;
import cn.jzvd.JZVideoPlayer;
import de.hdodenhof.circleimageview.CircleImageView;
import me.xiaocao.news.app.Api;
import me.xiaocao.news.app.Constants;
import me.xiaocao.news.app.MyApp;
import me.xiaocao.news.ui.about.AboutActivity;
import me.xiaocao.news.ui.jiemian.JieMianFragment;
import me.xiaocao.news.ui.setting.SettingsActivity;
import me.xiaocao.news.ui.video.VideoFragment;
import me.xiaocao.news.ui.zhihu.ZhiHuFragment;
import x.lib.ui.BaseActivity;
import x.lib.ui.TitleView;
import x.lib.utils.BarUtils;
import x.lib.utils.GlideUtils;
import x.lib.utils.SPUtils;
import x.lib.utils.SnackbarUtils;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private TitleView title;
    private JieMianFragment newsFragment;
    private VideoFragment videoFragment;
    private ZhiHuFragment zhiHuFragment;
//    private PicFragment picFragment;
//    private NewsFragment wangyiFragment;
//    private CollectionFragment collectionFragment;

    @Override
    protected boolean isMainTheme() {
        return true;
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        BarUtils.setStatusBarAlpha(activity, 80);
        title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setTitleText("界面新闻");
    }

    @Override
    protected void initInstance() {
        final View headerLayout = navView.getHeaderView(0);
        initThreadHelper();
        getThreadHelper().execute(new Runnable() {
            @Override
            public void run() {
                GlideUtils.loadImageView(activity, "https://avatars3.githubusercontent.com/u/19219372?v=4&s=460", (CircleImageView) headerLayout.findViewById(R.id.ivAvatar));
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, title.getToolar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        setChioceItem(0);
        navView.getMenu().getItem(0).setChecked(true);

    }

    private long exitTime;
    private final long EXIT_DURATION = 2000;

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        } else if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - exitTime) < EXIT_DURATION) {
                ((MyApp) activity.getApplication()).activityManagement.clearAllActivity();
                System.exit(0);
            } else {
                SnackbarUtils.with(title.getToolar())
                        .setMessage("再按一次退出")
                        .setMessageColor(getResources().getColor(android.R.color.white))
                        .setBgColor(getResources().getIntArray(R.array.colors)[SPUtils.getInstance().getInt(BaseActivity.isTheme, 0)])
                        .show();
                exitTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            JZVideoPlayer.releaseAllVideos();
            GoActivity(AboutActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        if (id == R.id.menuNews) {
            title.setTitleText("界面新闻");
            JZVideoPlayer.releaseAllVideos();
            setChioceItem(0);
        } else if (id == R.id.menuVideo) {
            title.setTitleText("网易视频");
            setChioceItem(2);
        }else if (id==R.id.menuRead){
            title.setTitleText("知乎日报");
            JZVideoPlayer.releaseAllVideos();
            setChioceItem(1);
        }else if (id == R.id.menuAbout) {
            JZVideoPlayer.releaseAllVideos();
            GoActivity(AboutActivity.class);
        } else if (id == R.id.menuSetting) {
            JZVideoPlayer.releaseAllVideos();
            GoActivity(SettingsActivity.class);
        }
        return true;
    }


    public void setChioceItem(int index) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (newsFragment == null) {
                    newsFragment = new JieMianFragment();
                    transaction.add(R.id.flContent, newsFragment, Constants.HOME_FRAGMENT);
                } else {
                    transaction.show(newsFragment);
                }
                break;
            case 1:
                if (zhiHuFragment == null) {
                    zhiHuFragment = new ZhiHuFragment();
                    transaction.add(R.id.flContent, zhiHuFragment, Constants.READ_FRAGMENT);
                } else {
                    transaction.show(zhiHuFragment);
                }
                break;
            case 2:
                if (videoFragment == null) {
                    videoFragment = new VideoFragment();
                    transaction.add(R.id.flContent, videoFragment, Constants.VIDEO_FRAGMENT);
                } else {
                    transaction.show(videoFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
        if (videoFragment != null) {
            transaction.hide(videoFragment);
        }
        if (zhiHuFragment != null) {
            transaction.hide(zhiHuFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

}
