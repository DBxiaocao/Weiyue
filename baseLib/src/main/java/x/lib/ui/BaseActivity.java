package x.lib.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;


import com.xiaocao.lib.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import x.lib.BaseApplication;
import x.lib.utils.EventBusUtil;
import x.lib.utils.SPUtils;
import x.lib.utils.SnackbarUtils;
import x.lib.utils.ThemeUtils;
import x.lib.utils.ThreadHelperFactory;

/**
 * className: BaseActivity
 * author: lijun
 * date: 17/6/26 18:28
 */

public abstract class BaseActivity extends AppCompatActivity implements SlidingPaneLayout.PanelSlideListener {
    public static final String isTheme = "sp.isTheme";
    public static final String isTextSize="sp.text.size";

    protected ThreadHelperFactory.ThreadHelper threadHelper;
    protected AppCompatActivity activity;

    public ThreadHelperFactory.ThreadHelper getThreadHelper() {
        return threadHelper;
    }

    protected void initThreadHelper() {
        threadHelper = ThreadHelperFactory.getInstance().createUIThreadHelper(this);
    }

    protected void initThreadHelper(Handler.Callback callback) {
        threadHelper = ThreadHelperFactory.getInstance().createUIThreadHelper(this, callback);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        getWindow().setEnterTransition(new Fade().setDuration(500));
//        getWindow().setExitTransition(new Fade().setDuration(500));
        ThemeUtils.changeTextSize(activity,ThemeUtils.getSizeCurrentTheme());
        if (isMainTheme()) {
            ThemeUtils.changeTheme(activity, ThemeUtils.getCurrentTheme());
        } else {
            ThemeUtils.changeThemeClose(activity, ThemeUtils.getCurrentTheme());
        }
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        if (isSupportSwipeBack()) {
            initSwipeBack();
        }
        setContentView(setContentViewResId());
        BaseApplication.activityManagement.pushActivity(this);
        ButterKnife.bind(activity);
        initTitle();
        initInstance();
    }


    protected abstract int setContentViewResId();

    protected abstract void initTitle();

    protected abstract void initInstance();

    private void initSwipeBack() {
        SlidingPaneLayout slidingPaneLayout = new SlidingPaneLayout(this);
        // 通过反射改变mOverhangSize的值为0，
        // 这个mOverhangSize值为菜单到右边屏幕的最短距离，
        // 默认是32dp，现在给它改成0
        try {
            Field overhangSize = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
            overhangSize.setAccessible(true);
            overhangSize.set(slidingPaneLayout, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        slidingPaneLayout.setPanelSlideListener(this);
        slidingPaneLayout.setSliderFadeColor(getResources()
                .getColor(android.R.color.transparent));
        // 左侧的透明视图
        View leftView = new View(this);
        leftView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        slidingPaneLayout.addView(leftView, 0);
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        // 右侧的内容视图
        ViewGroup decorChild = (ViewGroup) decorView.getChildAt(0);
        decorChild.setBackgroundColor(getResources()
                .getColor(android.R.color.white));
        decorView.removeView(decorChild);
        decorView.addView(slidingPaneLayout);
        // 为 SlidingPaneLayout 添加内容视图
        slidingPaneLayout.addView(decorChild, 1);
    }

    /**
     * 是否eventbus
     *
     * @return
     */
    protected boolean isRegisterEventBus() {
        return true;
    }

    /**
     * 是否是主页
     * @return
     */
    protected boolean isMainTheme() {
        return false;
    }

    /**
     * 是否支持滑动退出
     */
    protected boolean isSupportSwipeBack() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {

    }


    public void showErrNetWork(View view,String strMsg){
        SnackbarUtils.with(view)
                .setBgColor(getResources().getIntArray(R.array.colors)[SPUtils.getInstance().getInt(BaseActivity.isTheme, 0)])
                .setMessageColor(getResources().getColor(android.R.color.white))
                .setMessage(strMsg).show();
    }

    public void showSnackbar(View view,String msg){
        SnackbarUtils.with(view)
                .setBgColor(getResources().getIntArray(R.array.colors)[SPUtils.getInstance().getInt(BaseActivity.isTheme, 0)])
                .setMessageColor(getResources().getColor(android.R.color.white))
                .setMessage(msg).show();
    }

    /**
     * 跳转到下一个Activity
     *
     * @param cls
     */
    public void GoActivity(Class<?> cls) {

        Intent i1 = new Intent();
        i1.setClass(activity, cls);
        activity.startActivity(i1, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
    }

    /**
     * @param cls
     * @param
     */
    public void GoActivity(Class<?> cls, Bundle bundle) {

        Intent i1 = new Intent();
        if (null != bundle) {
            i1.putExtras(bundle);
        }
        i1.setClass(activity, cls);
        activity.startActivity(i1, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
    }

    /**
     * @param cls
     * @param
     */
    public void GoActivity(Class<?> cls, Bundle bundle, int requestCode) {

        Intent i1 = new Intent();
        if (null != bundle) {
            i1.putExtras(bundle);
        }
        i1.setClass(activity, cls);
        activity.startActivityForResult(i1, requestCode, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.activityManagement.popActivity(this);
        if (threadHelper != null)
            threadHelper.shutdown();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
        ButterKnife.unbind(activity);
        activity = null;
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {

    }

    @Override
    public void onPanelOpened(View panel) {
        onBackPressed();
        this.overridePendingTransition(R.anim.activity_swipe_in, R.anim.activity_swipe_out);
    }

    @Override
    public void onPanelClosed(View panel) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
