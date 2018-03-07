package me.xiaocao.news.model.request;

import me.xiaocao.news.app.Api;
import x.lib.http.request.get.GetRequest;

/**
 * description: ZhihuListRequest
 * author: lijun
 * date: 17/9/8 21:38
 */

public class ZhihuListRequest extends GetRequest {

    public int page;
    public boolean toDay;

    public ZhihuListRequest setPage(int page) {
        this.page = page;
        return this;
    }

    public ZhihuListRequest setToDay(boolean toDay) {
        this.toDay = toDay;
        return this;
    }

    @Override
    public String url() {
        if (toDay)
            return new StringBuffer().append(Api.ZHIHU_HOST).append(Api.ZHIHU_NEWS_LIST).toString();
        else
            return new StringBuffer().append(Api.ZHIHU_HOST).append(Api.ZHIHU_LAST_LIST).append(page).toString();
    }
}
