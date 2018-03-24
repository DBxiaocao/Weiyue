//package me.xiaocao.news.helper;
//
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.annotations.NonNull;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Function;
//import io.reactivex.schedulers.Schedulers;
//import me.xiaocao.news.db.CollectionHelper;
//import me.xiaocao.news.model.NewsEntity;
//import me.xiaocao.news.model.NewsList;
//import me.xiaocao.news.model.ZhihuDetail;
//import me.xiaocao.news.model.Wangyi;
//import me.xiaocao.news.model.db.CollectionVo;
//import me.xiaocao.news.model.event.ChannelEvent;
//import me.xiaocao.news.model.event.CollectionEvent;
//import me.xiaocao.news.model.event.NewsDetailEvent;
//import me.xiaocao.news.model.event.NewsListEvent;
//import me.xiaocao.news.model.event.WangyiEvent;
//import me.xiaocao.news.model.request.NewsChannelRequest;
//import me.xiaocao.news.model.request.NewsListRequest;
//import me.xiaocao.news.model.request.WangYiDetailRequest;
//import me.xiaocao.news.model.request.WangyiListRequest;
//import me.xiaocao.news.presenter.INewsPresenter;
//import x.http.HttpUtils;
//import x.http.result.ResponseResult;
//import x.http.util.JsonUtil;
//import x.lib.ui.BaseEvent;
//import x.lib.utils.EventBusUtil;
//import x.lib.utils.SPUtils;
//import x.lib.utils.StringUtils;
//
///**
// * description: NewsHelper
// * author: lijun
// * date: 17/8/27 14:57
// */
//
//public class NewsHelper implements INewsPresenter {
//    public static final String ChannelKey = "ChannelKey";
//
//    @Override
//    public void getChannel(NewsChannelRequest channelRequest) {
//        if (!StringUtils.isEmpty(SPUtils.getInstance().getString(ChannelKey))) {
//            List<String> list = (List<String>) JsonUtil.toObject(SPUtils.getInstance().getString(ChannelKey), NewsEntity.class).getResult();
//            EventBusUtil.sendEvent(new ChannelEvent(BaseEvent.code_success, list, null));
//        } else {
//            HttpUtils.async(channelRequest).map(new Function<ResponseResult, List<String>>() {
//                @Override
//                public List<String> apply(@NonNull ResponseResult responseResult) throws Exception {
//                    if (StringUtils.isEmpty(responseResult.result)) {
//                        return new ArrayList<String>();
//                    } else {
//                        JSONObject jsonObject = new JSONObject(responseResult.result);
//                        String result = jsonObject.getString("result");
//                        if (StringUtils.isEmpty(result)) {
//                            return new ArrayList<String>();
//                        } else {
//                            SPUtils.getInstance().put(ChannelKey, result);
//                            return (List<String>) JsonUtil.toObject(result, NewsEntity.class).getResult();
//                        }
//                    }
//                }
//            }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<String>>() {
//                @Override
//                public void onSubscribe(@NonNull Disposable d) {
//
//                }
//
//                @Override
//                public void onNext(@NonNull List<String> strings) {
//                    EventBusUtil.sendEvent(new ChannelEvent(BaseEvent.code_success, strings, null));
//                }
//
//                @Override
//                public void onError(@NonNull Throwable e) {
//                    EventBusUtil.sendEvent(new ChannelEvent(BaseEvent.code_err, e.getMessage(), null));
//                }
//
//                @Override
//                public void onComplete() {
//
//                }
//            });
//        }
//    }
//
//    @Override
//    public void getNewsList(final NewsListRequest listRequest) {
//        HttpUtils.async(listRequest).map(new Function<ResponseResult, List<NewsList>>() {
//            @Override
//            public List<NewsList> apply(@NonNull ResponseResult responseResult) throws Exception {
//                if (StringUtils.isEmpty(responseResult.result)) {
//                    return new ArrayList<NewsList>();
//                } else {
//                    JSONObject jsonObject = new JSONObject(responseResult.result);
//                    String result = jsonObject.getString("result");
//                    if (StringUtils.isEmpty(result)) {
//                        return new ArrayList<NewsList>();
//                    } else {
//                        JSONObject obj = new JSONObject(result);
//                        String list = obj.getString("result");
//                        if (StringUtils.isEmpty(list)) {
//                            return new ArrayList<NewsList>();
//                        } else {
//                            JSONObject objDate = new JSONObject(list);
//                            String date = objDate.getString("list");
//                            return JsonUtil.toObjects(date, NewsList.class);
//                        }
//                    }
//                }
//            }
//        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<NewsList>>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NonNull List<NewsList> newsLists) {
//                if (listRequest.start == 0) {
//                    EventBusUtil.sendEvent(new NewsListEvent(BaseEvent.code_refresh, newsLists, listRequest.channel));
//                } else {
//                    EventBusUtil.sendEvent(new NewsListEvent(BaseEvent.code_load, newsLists, listRequest.channel));
//                }
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                EventBusUtil.sendEvent(new NewsListEvent(BaseEvent.code_err, e.getMessage(), listRequest.channel));
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//    }
//
//    @Override
//    public void getNewsList(final WangyiListRequest request) {
//        HttpUtils.async(request).map(new Function<ResponseResult, List<Wangyi>>() {
//            @Override
//            public List<Wangyi> apply(@NonNull ResponseResult responseResult) throws Exception {
//                if (responseResult.success) {
//                    Map<String, List<Wangyi>> map = JsonUtil.stringToCollect(responseResult.result);
//                    return JsonUtil.toObjects(map.get(request.id).toString(), Wangyi.class);
//                } else {
//                    return null;
//                }
//            }
//        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Wangyi>>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NonNull List<Wangyi> news) {
//                if (news.isEmpty()) {
//                    EventBusUtil.sendEvent(new BaseEvent(BaseEvent.code_load_err, "暂无更多", request.id));
//                    return;
//                }
//                Collections.sort(news, new Comparator<Wangyi>() {
//                    @Override
//                    public int compare(Wangyi news, Wangyi t1) {
//                        return t1.getPtime().compareTo(news.getPtime());
//                    }
//                });
//                if (request.limit == 0)
//                    EventBusUtil.sendEvent(new WangyiEvent(BaseEvent.code_refresh, news, request.id));
//                else
//                    EventBusUtil.sendEvent(new WangyiEvent(BaseEvent.code_load, news, request.id));
//
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                EventBusUtil.sendEvent(new WangyiEvent(BaseEvent.code_err, e.getMessage(), request.id));
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//    }
//
//    @Override
//    public void getNewsDetail(final WangYiDetailRequest detailRequest) {
//        HttpUtils.async(detailRequest).map(new Function<ResponseResult, ZhihuDetail>() {
//            @Override
//            public ZhihuDetail apply(@NonNull ResponseResult responseResult) throws Exception {
//                if (responseResult.success) {
//                    Map<String, ZhihuDetail> map = JsonUtil.stringToCollect(responseResult.result);
//                    return JsonUtil.toObject(JsonUtil.GsonString(map.get(detailRequest.id)), ZhihuDetail.class);
//                } else {
//                    return null;
//                }
//            }
//        }).subscribeOn(Schedulers.newThread()).subscribe(new Observer<ZhihuDetail>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NonNull ZhihuDetail news) {
//                if (news != null)
//                    EventBusUtil.sendEvent(new NewsDetailEvent(BaseEvent.code_success, news, detailRequest.id));
//                else
//                    EventBusUtil.sendEvent(new NewsDetailEvent(BaseEvent.code_err, "获取数据失败", detailRequest.id));
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                EventBusUtil.sendEvent(new NewsDetailEvent(BaseEvent.code_err, e.getMessage(), detailRequest.id));
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//    }
//
//    @Override
//    public void getCollection(int offset) {
//        List<CollectionVo> dbs = CollectionHelper.getPageQuery(offset);
//        if (offset == 0)
//            if (dbs != null && dbs.size() > 0) {
//                EventBusUtil.sendEvent(new CollectionEvent(BaseEvent.code_refresh, dbs, null));
//            } else {
//                EventBusUtil.sendEvent(new CollectionEvent(BaseEvent.code_err, "暂无本地收藏", null));
//            }
//        else
//            if (dbs!=null && dbs.size()>0){
//                EventBusUtil.sendEvent(new CollectionEvent(BaseEvent.code_load,dbs,null));
//            }else {
//                EventBusUtil.sendEvent(new CollectionEvent(BaseEvent.code_load_err,"暂无更多",null));
//            }
//    }
//
//}
