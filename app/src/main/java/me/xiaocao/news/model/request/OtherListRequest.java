package me.xiaocao.news.model.request;

import me.xiaocao.news.app.Api;
import x.lib.http.request.get.GetRequest;

/**
 * description: HomeListRequest
 * author: lijun
 * date: 18/1/4 19:55
 */

public class OtherListRequest extends GetRequest {

    private int page;
    private String channel;


    public OtherListRequest setPage(int page) {
        this.page = page;
        return this;
    }


    public OtherListRequest setChannel(String channel) {
        this.channel = channel;
        return this;
    }


    //https://appapi.jiemian.com/v4/5.3.0/10001/cate/117/0/1/36/3609.json
    @Override
    public String url() {
        return new StringBuffer().append(Api.JIEMIAN_HOST).append(Api.JIEMIAN_HEAD).append(channel).append("0/").append(page).append("/36/").append(Api.JIEMIAN_END).toString();
    }
}
