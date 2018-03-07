package me.xiaocao.news.ui.setting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Arrays;
import java.util.List;

import me.xiaocao.news.R;
import me.xiaocao.news.ui.adapter.BaseListAdapter;
import me.xiaocao.news.app.MyApp;
import me.xiaocao.news.ui.SplashActivity;
import x.lib.ui.BaseActivity;
import x.lib.utils.AppUtils;
import x.lib.utils.CleanUtils;
import x.lib.utils.DialogUtil;
import x.lib.utils.SPUtils;
import x.lib.utils.SnackbarUtils;
import x.lib.utils.ThreadHelperFactory;

/**
 * description: SettingsFragment
 * author: lijun
 * date: 17/8/28 10:43
 */

public class SettingsFragment extends PreferenceFragment {
    private int checkPosition;
    private int txtSizeSelect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        checkPosition = SPUtils.getInstance().getInt(BaseActivity.isTheme, 0);
        final int colors[] = getResources().getIntArray(R.array.colors);

//        final CheckBoxPreference checkboxPref = (CheckBoxPreference) getPreferenceManager()
//                .findPreference(getString(R.string.key_read));
//        checkboxPref.setChecked(SPUtils.getInstance().getBoolean(Constants.isWeb));
//        checkboxPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                boolean checked = Boolean.valueOf(newValue.toString());
//                SPUtils.getInstance().put(Constants.isWeb, checked);
//                return true;
//            }
//        });

        final Preference clearPref = getPreferenceManager().findPreference(getString(R.string.key_clear));
        try {
            clearPref.setSummary(CleanUtils.getCacheSize(MyApp.getInstance().getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        clearPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SnackbarUtils.with(getView()).setBgColor(colors[SPUtils.getInstance().getInt(BaseActivity.isTheme, 0)]).setMessageColor(getResources().getColor(android.R.color.white)).setMessage("清除成功了").show();
                CleanUtils.cleanCustomCache(MyApp.getInstance().getCacheDir());
                try {
                    clearPref.setSummary(CleanUtils.getCacheSize(MyApp.getInstance().getCacheDir()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        final Preference versionPref = getPreferenceManager().findPreference(getString(R.string.key_version));
        versionPref.setSummary(AppUtils.getAppVersionName());
        final Preference textSizePref = getPreferenceScreen().findPreference(getString(R.string.key_text_size));
        txtSizeSelect = SPUtils.getInstance().getInt(BaseActivity.isTextSize, 0);
        textSizePref.setSummary("当前字体大小:" + getResources().getStringArray(R.array.text_size)[txtSizeSelect]);
        textSizePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogUtil.showDialog(getActivity()).setTitle("字体大小").setSingleChoiceItems(getResources().getStringArray(R.array.text_size), txtSizeSelect, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        txtSizeSelect = i;
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SPUtils.getInstance().put(BaseActivity.isTextSize, txtSizeSelect);
                        textSizePref.setSummary("当前字体大小:" + getResources().getStringArray(R.array.text_size)[txtSizeSelect]);
                        dialogInterface.dismiss();
                        onKillStartApp();
                    }
                }).create().show();
                return true;
            }
        });

        final Preference themePref = getPreferenceManager().findPreference(getString(R.string.key_theme));
        themePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                final BaseListAdapter<Integer> listAdapter;
                Integer[] res = new Integer[]{R.drawable.red_round, R.drawable.brown_round, R.drawable.blue_round,
                        R.drawable.blue_grey_round, R.drawable.yellow_round, R.drawable.deep_purple_round,
                        R.drawable.pink_round, R.drawable.green_round};
                List<Integer> list = Arrays.asList(res);
                listAdapter = new BaseListAdapter<>(R.layout.grid_item_theme, list);
                listAdapter.setOnCallBackData(new BaseListAdapter.OnCallBackData<Integer>() {
                    @Override
                    public void convertView(BaseViewHolder helper, Integer item) {
                        ImageView img_1 = helper.getView(R.id.img_1);
                        img_1.setImageResource(item);
                        ImageView img_2 = helper.getView(R.id.img_2);
                        if (checkPosition == listAdapter.getItemPosition(item)) {
                            img_2.setVisibility(View.VISIBLE);
                        } else {
                            img_2.setVisibility(View.GONE);
                        }
                    }
                });
                listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        checkPosition = position;
                        listAdapter.notifyDataSetChanged();
                    }
                });
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_theme_layout, null);
                RecyclerView recycler = (RecyclerView) view.findViewById(R.id.recycler);
                recycler.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                recycler.setAdapter(listAdapter);
                DialogUtil.showDialog(getActivity()).setTitle("选择主题").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        onKillStartApp();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
                return true;
            }
        });
    }

    private void onKillStartApp() {
        SnackbarUtils.with(getView())
                .setBgColor(getResources().getIntArray(R.array.colors)[SPUtils.getInstance().getInt(BaseActivity.isTheme, 0)])
                .setMessageColor(getResources().getColor(android.R.color.white))
                .setMessage("设置成功，APP将在两秒后重启!").show();
        SPUtils.getInstance().put(BaseActivity.isTheme, checkPosition);
        ThreadHelperFactory.getInstance().createUIThreadHelper(getActivity()).executeDelay(new Runnable() {
            @Override
            public void run() {
                Intent mStartActivity = new Intent(MyApp.getInstance(), SplashActivity.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(MyApp.getInstance(), mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager) MyApp.getInstance().getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis(), mPendingIntent);
                MyApp.activityManagement.clearAllActivity();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
//                                android.os.Process.killProcess(android.os.Process.myPid());
                //重启app代码
//                                Intent intent = MyApp.getInstance().getBaseContext().getPackageManager()
//                                        .getLaunchIntentForPackage(MyApp.getInstance().getBaseContext().getPackageName());
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
            }
        }, 2000);
    }

}
