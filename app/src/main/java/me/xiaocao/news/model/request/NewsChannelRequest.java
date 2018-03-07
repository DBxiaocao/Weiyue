package me.xiaocao.news.model.request;

import me.xiaocao.news.app.Api;
import x.http.util.RequestUtil;

/**
 * description: NewsChannel
 * author: lijun
 * date: 17/8/27 14:59
 */

public class NewsChannelRequest extends NewsRequest{
    public NewsChannelRequest(String appkey) {
        super(appkey);
    }

    @Override
    public String url() {
        return RequestUtil.getUtil().getUrl(Api.NEWS_CHANNEL);
    }
}
