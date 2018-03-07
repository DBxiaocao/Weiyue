package me.xiaocao.news.model.request;


import me.xiaocao.news.app.Api;
import x.http.request.GetRequest;
import x.http.util.RequestUtil;

/**
 * className: MainRequest
 * author: lijun
 * date: 17/6/29 15:51
 */

public class VideoRequest extends GetRequest {
    public int limit;
    public String id;

    public VideoRequest setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public VideoRequest setId(String id) {
        this.id = id;
        return this;
    }


    @Override
    public String url() {
        return RequestUtil.getUtil().getUrl(Api.HTTP_VIDEO_HEAD + id + "/y/" + limit + Api.END_URL);
    }

}
