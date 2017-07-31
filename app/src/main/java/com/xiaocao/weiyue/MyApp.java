package com.xiaocao.weiyue;


import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.xiaocao.weiyue.dao.ChannelDao;
import com.xiaocao.weiyue.dao.CollectionDao;


import x.lib.BaseApplication;

import x.http.HttpUtils;
import x.http.util.RequestUtil;

/**
 * className: MyApp
 * author: lijun
 * date: 17/6/29 16:49
 */

public class MyApp extends BaseApplication {
    private static final String DB_NAME_CHANNEL = "channel.db";
    private static final String DB_NAME_COLLECTION = "collection.db";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        HttpUtils.init(Api.NETEAST_HOST, instance);
        RequestUtil.getUtil().setServerUrl(Api.NETEAST_HOST);
        ChannelDao.initDatabase(DB_NAME_CHANNEL);
        CollectionDao.initDatabase(DB_NAME_COLLECTION);
    }


}
