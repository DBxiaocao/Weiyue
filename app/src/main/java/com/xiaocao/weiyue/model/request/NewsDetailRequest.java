package com.xiaocao.weiyue.model.request;

import com.xiaocao.weiyue.Api;

import x.http.request.GetRequest;
import x.http.util.RequestUtil;

/**
 * className: NewsDetailRequest
 * author: lijun
 * date: 17/7/6 10:56
 */

public class NewsDetailRequest extends GetRequest {
    public String id;

    public NewsDetailRequest setId(String newsId) {
        this.id = newsId;
        return this;
    }

    @Override
    public String url() {
        return RequestUtil.getUtil().getUrl(Api.NEWS_DETAIL + id + Api.ENDDETAIL_URL);
    }
}
