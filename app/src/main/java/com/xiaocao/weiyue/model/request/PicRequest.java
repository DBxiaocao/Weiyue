package com.xiaocao.weiyue.model.request;

import com.xiaocao.weiyue.Api;

import x.http.request.GetRequest;
import x.http.util.RequestUtil;

/**
 * description: PicRequest
 * author: xiaocao
 * date: 17/7/23 00:17
 */

public class PicRequest extends GetRequest {
//    public int cid;
    public int pager_offset;

    public PicRequest setPager_offset(int pager_offset) {
        this.pager_offset = pager_offset;
        return this;
    }

//    public PicRequest setCid(int cid) {
//        this.cid = cid;
//        return this;
//    }

    @Override
    public String url() {
        return RequestUtil.getUtil().getHtmlUrl(Api.PIC_URL+pager_offset);
//        return RequestUtil.getUtil().getHtmlUrl(Api.PIC_URL+"?cid="+cid+"&pager_offset="+pager_offset);
    }
}
