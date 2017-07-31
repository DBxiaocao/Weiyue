package com.xiaocao.weiyue.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaocao.weiyue.Constants;
import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.dao.ChannelDao;
import com.xiaocao.weiyue.model.ChannelVo;

import java.util.ArrayList;
import java.util.List;

import x.lib.ui.BaseActivity;
import x.lib.utils.SPUtils;

public class SplashActivity extends BaseActivity {

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initInstance() {
        initThreadHelper();
        threadHelper.executeDelay(new Runnable() {
            @Override
            public void run() {
                if (!SPUtils.getInstance(Constants.SP_KEY_CHANNEL).getBoolean(Constants.SP_KEY_CHANNEL, false)) {
                    List<ChannelVo> list = new ArrayList<>();
                    String[] newsTitle = getResources().getStringArray(R.array.news_channel_name);
                    String[] newsId = getResources().getStringArray(R.array.news_channel_id);
                    for (int i = 0; i < newsTitle.length; i++) {
                        ChannelVo channel = new ChannelVo();
                        channel.setNewsId(newsId[i]);
                        if (i<=4){
                            channel.setType(ChannelVo.TYPE_TOP);
                        }else {
                            channel.setType(ChannelVo.TYPE_BOTTOM);
                        }
                        channel.setTitle(newsTitle[i]);
                        channel.setId((i+1));
                        list.add(channel);
                    }
                    ChannelDao.insertsChannel(list);
                    SPUtils.getInstance(Constants.SP_KEY_CHANNEL).put(Constants.SP_KEY_CHANNEL, true);
                }
                GoActivity(MainActivity.class);
            }
        }, 1000);
    }
}
