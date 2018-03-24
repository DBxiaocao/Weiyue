//package me.xiaocao.news.helper;
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
//import me.xiaocao.news.model.Guokr;
//import me.xiaocao.news.model.ZhihuDetail;
//import me.xiaocao.news.model.event.GuokrListEvent;
//import me.xiaocao.news.model.event.ReadDetailEvent;
//import me.xiaocao.news.model.request.GuokrDetailRequest;
//import me.xiaocao.news.model.request.GuokrListRequest;
//import me.xiaocao.news.presenter.IGuokrPresenter;
//import x.lib.ui.BaseEvent;
//import x.lib.utils.EventBusUtil;
//import x.lib.utils.StringUtils;
//
///**
// * description: GuokrHelper
// * author: lijun
// * date: 17/9/8 22:09
// */
//
//public class GuokrHelper implements IGuokrPresenter {
//
//    @Override
//    public void getGuokrList(final GuokrListRequest listRequest) {
//        HttpUtils.async(listRequest).map(new Function<ResponseResult, List<Guokr>>() {
//            @Override
//            public List<Guokr> apply(@NonNull ResponseResult responseResult) throws Exception {
//                if (StringUtils.isEmpty(responseResult.result)) {
//                    return new ArrayList<>();
//                } else {
//                    JSONObject jsonObject = new JSONObject(responseResult.result);
//                    String result = jsonObject.getString("result");
//                    if (StringUtils.isEmpty(result)) {
//                        return new ArrayList<>();
//                    } else {
//                        return JsonUtil.toObjects(result, Guokr.class);
//                    }
//                }
//            }
//        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Guokr>>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NonNull List<Guokr> guokrs) {
//                if (listRequest.offset==0){
//                    EventBusUtil.sendEvent(new GuokrListEvent(BaseEvent.code_refresh,guokrs,null));
//                }else {
//                    EventBusUtil.sendEvent(new GuokrListEvent(BaseEvent.code_load,guokrs,null));
//                }
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                EventBusUtil.sendEvent(new GuokrListEvent(BaseEvent.code_err,e.getMessage(),null));
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
//    public void getGuokrDetail(GuokrDetailRequest request) {
//        HttpUtils.async(request).map(new Function<ResponseResult, ZhihuDetail>() {
//            @Override
//            public ZhihuDetail apply(@NonNull ResponseResult responseResult) throws Exception {
//                if (StringUtils.isEmpty(responseResult.result)){
//                    return new ZhihuDetail();
//                }else {
//                    String result=new JSONObject(responseResult.result).getString("result");
//                    if (StringUtils.isEmpty(result)){
//                        return new ZhihuDetail();
//                    }else {
//                        return JsonUtil.toObject(result,ZhihuDetail.class);
//                    }
//                }
//            }
//        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ZhihuDetail>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NonNull ZhihuDetail s) {
//                EventBusUtil.sendEvent(new ReadDetailEvent(BaseEvent.code_success,s,null));
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                EventBusUtil.sendEvent(new ReadDetailEvent(BaseEvent.code_err,e.getMessage(),null));
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//    }
//
//}
