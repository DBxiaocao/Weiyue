package me.xiaocao.news.model.request;


import me.xiaocao.news.app.Api;
import x.http.request.GetRequest;
import x.http.util.RequestUtil;

/**
 * description: PicRequest
 * author: xiaocao
 * date: 17/7/23 00:17
 */

public class PicRequest extends GetRequest {
    public int pager_offset;

    public PicRequest setPager_offset(int pager_offset) {
        this.pager_offset = pager_offset;
        return this;
    }


    @Override
    public String url() {
        return RequestUtil.getUtil().getHtmlUrl(Api.PIC_URL+pager_offset);
    }
}
