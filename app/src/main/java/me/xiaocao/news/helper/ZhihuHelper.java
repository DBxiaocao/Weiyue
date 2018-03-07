package me.xiaocao.news.helper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import me.xiaocao.news.model.ReadDetail;
import me.xiaocao.news.model.Zhihu;
import me.xiaocao.news.model.event.ReadDetailEvent;
import me.xiaocao.news.model.event.ZhihuEvent;
import me.xiaocao.news.model.request.ZhiHuDetailRequest;
import me.xiaocao.news.model.request.ZhihuListRequest;
import me.xiaocao.news.presenter.IZhiHuPresenter;
import x.http.HttpUtils;
import x.http.result.ResponseResult;
import x.http.util.JsonUtil;
import x.lib.ui.BaseEvent;
import x.lib.utils.EventBusUtil;
import x.lib.utils.StringUtils;

/**
 * description: ZhihuHelper
 * author: lijun
 * date: 17/9/8 21:48
 */

public class ZhihuHelper implements IZhiHuPresenter {
    @Override
    public void getZhiHuList(final ZhihuListRequest listRequest) {
        HttpUtils.async(listRequest).map(new Function<ResponseResult, List<Zhihu>>() {
            @Override
            public List<Zhihu> apply(@NonNull ResponseResult responseResult) throws Exception {
                if (StringUtils.isEmpty(responseResult.result)) {
                    return new ArrayList<>();
                } else {
                    JSONObject jsonObject = new JSONObject(responseResult.result);
                    String result = jsonObject.getString("stories");
                    if (StringUtils.isEmpty(result)) {
                        return new ArrayList<>();
                    } else {
                        return JsonUtil.toObjects(result, Zhihu.class);
                    }
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Zhihu>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Zhihu> zhihus) {
                if (zhihus != null && zhihus.size() > 0)
                    if (listRequest.toDay) {
                        EventBusUtil.sendEvent(new ZhihuEvent(BaseEvent.code_refresh, zhihus, null));
                    } else {
                        EventBusUtil.sendEvent(new ZhihuEvent(BaseEvent.code_load, zhihus, null));
                    }
                else
                    EventBusUtil.sendEvent(new ZhihuEvent(BaseEvent.code_err,"暂无数据",null));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                EventBusUtil.sendEvent(new ZhihuEvent(BaseEvent.code_err, e.getMessage(), null));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getZhiDetail(ZhiHuDetailRequest detailRequest) {
        HttpUtils.async(detailRequest).map(new Function<ResponseResult, ReadDetail>() {
            @Override
            public ReadDetail apply(@NonNull ResponseResult responseResult) throws Exception {
                if (StringUtils.isEmpty(responseResult.result)){
                    return null;
                }else {
                    return JsonUtil.toObject(responseResult.result,ReadDetail.class);
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ReadDetail>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ReadDetail s) {
                EventBusUtil.sendEvent(new ReadDetailEvent(BaseEvent.code_success,s,null));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                EventBusUtil.sendEvent(new ReadDetailEvent(BaseEvent.code_err,e.getMessage(),null));
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
