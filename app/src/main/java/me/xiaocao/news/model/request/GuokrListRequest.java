package me.xiaocao.news.model.request;

import me.xiaocao.news.app.Api;
import x.http.request.GetRequest;
import x.http.util.RequestUtil;

/**
 * description: GankrListRequest
 * author: lijun
 * date: 17/9/8 22:06
 */

public class GuokrListRequest extends GetRequest {
    //retrieve_type=by_channel&channel_key=hot&offset=0
    public String retrieve_type;
    public String channel_key;
    public int offset;

    public GuokrListRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }


    public GuokrListRequest setRetrieve_type(String retrieve_type) {
        this.retrieve_type = retrieve_type;
        return this;
    }

    public GuokrListRequest setChannel_key(String channel_key) {
        this.channel_key = channel_key;
        return this;
    }


    @Override
    public String url() {
        return RequestUtil.getUtil().getUrl(Api.GUOKR_LIST);
    }
}
