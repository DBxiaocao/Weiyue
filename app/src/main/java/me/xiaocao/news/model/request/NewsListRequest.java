//package me.xiaocao.news.model.request;
//
//import me.xiaocao.news.app.Api;
//import x.http.util.RequestUtil;
//
///**
// * description: NewsListRequest
// * author: lijun
// * date: 17/8/27 16:10
// */
//
//public class NewsListRequest extends NewsRequest{
//
//    public String channel;
//    public int num=20;
//    public int start;
//    public NewsListRequest(String appkey,String channel,int start) {
//        super(appkey);
//        this.channel=channel;
//        this.start=start;
//    }
//
//    @Override
//    public String url() {
//        return RequestUtil.getUtil().getUrl(Api.NEWS_LIST);
//    }
//}
