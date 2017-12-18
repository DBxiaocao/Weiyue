package com.xiaocao.weiyue.ui.picture;

import com.xiaocao.weiyue.model.Pics;
import com.xiaocao.weiyue.model.event.PicEvent;
import com.xiaocao.weiyue.model.request.PicRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import x.http.HttpUtils;
import x.http.result.ResponseResult;
import x.http.util.JsonUtil;
import x.lib.http.rx.RxSchedulers;
import x.lib.ui.BaseEvent;
import x.lib.ui.mvp.BasePresenterImpl;
import x.lib.utils.EventBusUtil;

/**
 * description: PicPresenterImpl
 * author: lijun
 * date: 17/12/18 21:08
 */

public class PicPresenterImpl extends BasePresenterImpl<PicContract.IView> implements PicContract.IPresenter {
    @Override
    public void getPicList(final PicRequest request) {
        HttpUtils.async(request).map(new Function<ResponseResult, List<Pics>>() {
            @Override
            public List<Pics> apply(@NonNull ResponseResult responseResult) throws Exception {
                JSONObject obj = new JSONObject(responseResult.result);
                String result;
                if (obj != null && obj.has("results")) {
                    result = obj.getString("results");
                    return JsonUtil.toObjects(result, Pics.class);
                } else {
                    return new ArrayList<>();
                }
            }
        })
                .compose(RxSchedulers.<List<Pics>>composeObs())
                .subscribe(new Observer<List<Pics>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Pics> picses) {
                        if (isAttachView())
                            if (picses.isEmpty())
                                getView().onErrMsg("暂无数据");
                            else if (request.pager_offset == 1)
                                getView().picReFresh(picses);
                            else
                                getView().picLoad(picses);
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
