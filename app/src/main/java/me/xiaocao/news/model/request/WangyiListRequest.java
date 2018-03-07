package me.xiaocao.news.model.request;


import me.xiaocao.news.app.Api;
import x.http.request.GetRequest;
import x.http.util.RequestUtil;

/**
 * className: NewsRequest
 * author: lijun
 * date: 17/7/4 17:25
 */

public class WangyiListRequest extends GetRequest {
    public String id;


    public int limit;

    public WangyiListRequest setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public WangyiListRequest setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String url() {
        return RequestUtil.getUtil().getUrl(Api.HTTP_NEWS_HEAD + id + "/" + limit + Api.END_URL);
    }
}
