//package me.xiaocao.news.helper;
//
//
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.annotations.NonNull;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Function;
//import me.xiaocao.news.model.Pics;
//import me.xiaocao.news.model.event.PicEvent;
//import me.xiaocao.news.model.request.PicRequest;
//import me.xiaocao.news.presenter.IPicPresenter;
//import x.http.HttpUtils;
//import x.http.result.ResponseResult;
//import x.http.util.JsonUtil;
//import x.lib.ui.BaseEvent;
//import x.lib.utils.EventBusUtil;
//
//
///**
// * description: PicHelper
// * author: xiaocao
// * date: 17/7/23 00:14
// */
//
//public class PicHelper implements IPicPresenter {
//
//    @Override
//    public void loadPic(final PicRequest request) {
//        HttpUtils.async(request).map(new Function<ResponseResult, List<Pics>>() {
//            @Override
//            public List<Pics> apply(@NonNull ResponseResult responseResult) throws Exception {
//                JSONObject obj = new JSONObject(responseResult.result);
//                String result;
//                if (obj != null && obj.has("results")) {
//                    result = obj.getString("results");
//                    return JsonUtil.toObjects(result, Pics.class);
//                } else {
//                    return new ArrayList<>();
//                }
//            }
//        }).subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<Pics>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull List<Pics> picses) {
//                        if (picses.isEmpty()) {
//                            EventBusUtil.sendEvent(new PicEvent(BaseEvent.code_load_err, "暂无更多", null));
//                            return;
//                        }
//                        if (request.pager_offset == 1) {
//                            EventBusUtil.sendEvent(new PicEvent(BaseEvent.code_refresh, picses, null));
//                        } else {
//                            EventBusUtil.sendEvent(new PicEvent(BaseEvent.code_load, picses, null));
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        EventBusUtil.sendEvent(new PicEvent(BaseEvent.code_err, e.getMessage(), null));
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
////        RxJsoupNetWork.getInstance().getApi(request.url(), new RxJsoupNetWorkListener<List<Pics>>() {
////            @Override
////            public void onNetWorkStart() {
////
////            }
////
////            @Override
////            public void onNetWorkError(Throwable e) {
////                EventBusUtil.sendEvent(new PicEvent(BaseEvent.code_err, e.getMessage(), null));
////            }
////
////            @Override
////            public void onNetWorkComplete() {
////
////            }
////
////            @Override
////            public void onNetWorkSuccess(List<Pics> picses) {
////                if (picses.isEmpty()) {
////                    EventBusUtil.sendEvent(new PicEvent(BaseEvent.code_load_err, "暂无更多", null));
////                    return;
////                }
////                if (request.pager_offset == 1) {
////                    EventBusUtil.sendEvent(new PicEvent(BaseEvent.code_refresh, picses, null));
////                } else {
////                    EventBusUtil.sendEvent(new PicEvent(BaseEvent.code_load, picses, null));
////                }
////            }
////
////            @Override
////            public List<Pics> getT(Document document) {
////                List<Pics> pics = new ArrayList<Pics>();
////                Elements elements = document.select("div.img_single").select("img.height_min");
////                for (Element element : elements) {
////                    Pics pic = new Pics();
////                    pic.setImgUrl(element.absUrl("src"));
////                    pics.add(pic);
////                }
////                return pics;
////            }
////        });
//
//    }
//}
