//package me.xiaocao.news.model.request;
//
//
//import me.xiaocao.news.app.Api;
//import x.http.request.GetRequest;
//import x.http.util.RequestUtil;
//
///**
// * className: WangYiDetailRequest
// * author: lijun
// * date: 17/7/6 10:56
// */
//
//public class WangYiDetailRequest extends GetRequest {
//    public String id;
//
//    public WangYiDetailRequest setId(String newsId) {
//        this.id = newsId;
//        return this;
//    }
//
//    @Override
//    public String url() {
//        return RequestUtil.getUtil().getUrl(Api.NEWS_DETAIL + id + Api.WANGYI_URL);
//    }
//}
