package me.xiaocao.news.model.request;

import x.http.request.GetRequest;
import x.http.util.RequestUtil;

/**
 * description: GuokrDetailRequest
 * author: lijun
 * date: 17/9/8 23:02
 */

public class GuokrDetailRequest extends GetRequest {
    public String url;

    public GuokrDetailRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String url() {
        return RequestUtil.getUtil().getHtmlUrl(url);
    }
}
