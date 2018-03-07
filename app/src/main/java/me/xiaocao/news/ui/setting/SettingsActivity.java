package me.xiaocao.news.ui.setting;

import me.xiaocao.news.R;
import x.lib.ui.BaseActivity;
import x.lib.ui.TitleView;


/**
 * description: SettingsActivity
 * author: lijun
 * date: 17/8/28 上午10:39
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initTitle() {
        TitleView title=new TitleView(activity,findViewById(R.id.toolbar));
        title.setBack(activity);
        title.setTitleText("设置");
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    protected void initInstance() {
        getFragmentManager().beginTransaction().replace(R.id.flContent, new SettingsFragment()).commit();
    }

}
