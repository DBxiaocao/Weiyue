package com.xiaocao.weiyue.model.request;

import x.http.request.PostRequest;
import x.http.util.RequestUtil;

/**
 * description: TestRequest
 * author: lijun
 * date: 17/8/20 15:36
 */

public class TestRequest extends PostRequest{
    public String mmp;
    public TestRequest setMMP(String mmp){
        this.mmp=mmp;
        return this;
    }
    @Override
    public String url() {
        return RequestUtil.getUtil().getHtmlUrl("http://www.gifcool.com/appdata/getlist.php?current=1&pagesize=20&typeid=12&version=1.0.0");
    }
}
