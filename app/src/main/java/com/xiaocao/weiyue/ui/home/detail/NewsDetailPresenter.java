package com.xiaocao.weiyue.ui.home.detail;

import com.xiaocao.weiyue.model.NewsDetail;
import com.xiaocao.weiyue.model.request.NewsDetailRequest;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import x.http.result.IHttpResult;
import x.lib.ui.mvp.BasePresenterImpl;

/**
 * description: NewsDetailPresenter
 * author: lijun
 * date: 17/12/18 19:58
 */

public class NewsDetailPresenter extends BasePresenterImpl<NewsDetailContract.DetailView> implements NewsDetailContract.DetailPresenter {

    private NewsDetailContract.DetailModel model;

    public NewsDetailPresenter() {
        model = new NewsDetailModel();
    }

    @Override
    public void getDetail(NewsDetailRequest request) {
        model.getNewsDetail(request)
                .subscribe(new Observer<NewsDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(NewsDetail newsDetail) {
                        if (isAttachView())
                            getView().setDetail(newsDetail);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isAttachView())
                            getView().onErrMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void saveDb(String url, String title, String id) {
        model.mSaveDb(url, title, id, new IHttpResult<String>() {
            @Override
            public void onSuccess(String data) {
                if (isAttachView())
                    getView().saveDb(data);
            }

            @Override
            public void onErrMsg(String msg) {
                if (isAttachView())
                    getView().onErrMsg(msg);
            }
        });
    }
}
