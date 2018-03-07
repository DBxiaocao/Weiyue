package me.xiaocao.news.helper;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.xiaocao.news.model.Video;
import me.xiaocao.news.model.request.VideoRequest;
import me.xiaocao.news.presenter.IVideoPresenter;
import x.http.HttpUtils;
import x.http.result.ResponseResult;
import x.http.util.JsonUtil;
import x.lib.ui.BaseEvent;
import x.lib.utils.EventBusUtil;

/**
 * className: VideoHelper
 * author: lijun
 * date: 17/6/30 18:06
 */

public class VideoHelper implements IVideoPresenter {
    @Override
    public void getVideoList(final VideoRequest request) {
        HttpUtils.async(request).map(new Function<ResponseResult, List<Video>>() {
            @Override
            public List<Video> apply(@NonNull ResponseResult responseResult) throws Exception {
                if (responseResult.success) {
                    Map<String, List<Video>> map = JsonUtil.stringToCollect(responseResult.result);
                    return JsonUtil.toObjects(map.get(request.id).toString(), Video.class);
                } else {
                    return null;
                }
            }
        }).subscribeOn(Schedulers.newThread()).subscribe(new Observer<List<Video>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Video> tests) {
                if (tests.isEmpty()) {
                    EventBusUtil.sendEvent(new BaseEvent(BaseEvent.code_err, "暂无数据", request.id));
                } else {
                    if (request.limit == 0)
                        EventBusUtil.sendEvent(new BaseEvent(BaseEvent.code_refresh, tests, request.id));
                    else
                        EventBusUtil.sendEvent(new BaseEvent(BaseEvent.code_load, tests, request.id));
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                EventBusUtil.sendEvent(new BaseEvent(BaseEvent.code_err, e.getMessage(), request.id));
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
