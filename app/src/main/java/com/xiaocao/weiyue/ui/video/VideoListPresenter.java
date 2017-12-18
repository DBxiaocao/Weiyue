package com.xiaocao.weiyue.ui.video;

import android.support.annotation.NonNull;

import com.xiaocao.weiyue.model.Video;
import com.xiaocao.weiyue.model.request.VideoRequest;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import x.http.HttpUtils;
import x.http.result.ResponseResult;
import x.http.util.JsonUtil;
import x.lib.http.rx.RxSchedulers;
import x.lib.ui.BaseEvent;
import x.lib.ui.mvp.BasePresenterImpl;
import x.lib.utils.EventBusUtil;

/**
 * description: VideoListPresenter
 * author: lijun
 * date: 17/12/18 20:32
 */

public class VideoListPresenter extends BasePresenterImpl<VideoListContract.IView> implements VideoListContract.IPresenter {
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
        }).compose(RxSchedulers.<List<Video>>composeObs()).subscribe(new Observer<List<Video>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(@NonNull List<Video> videos) {
                if (isAttachView())
                    if (videos.isEmpty())
                        getView().onErrMsg("暂无数据");
                    else if (request.limit == 0)
                        getView().videoRefresh(videos);
                    else
                        getView().videoLoad(videos);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (isAttachView())
                    getView().onErrMsg(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
