package me.xiaocao.news.model.request;

import me.xiaocao.news.app.Api;
import x.http.request.GetRequest;
import x.http.util.RequestUtil;

/**
 * description: ZhiHuDetailRequest
 * author: lijun
 * date: 17/9/8 21:45
 */

public class ZhiHuDetailRequest extends GetRequest {

    private String id;

    public ZhiHuDetailRequest setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String url() {
        if (!id.contains("http")) {
            return RequestUtil.getUtil().getHtmlUrl(new StringBuffer().append(Api.ZHIHU_HOST).append(Api.ZHIHU_MSG_DETAIL).append(id).toString());
        } else {
            return RequestUtil.getUtil().getHtmlUrl(id);
        }
    }
}
