package me.xiaocao.news.model.request;

import me.xiaocao.news.app.Api;
import x.lib.http.request.get.GetRequest;

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
            return new StringBuffer().append(Api.ZHIHU_HOST).append(Api.ZHIHU_MSG_DETAIL).append(id).toString();
        } else {
            return new StringBuffer().append(Api.ZHIHU_HOST).append(Api.ZHIHU_MSG_DETAIL).toString();
        }
    }
}
