package com.xiaocao.weiyue.ui.home.detail;

import com.xiaocao.weiyue.model.NewsDetail;
import com.xiaocao.weiyue.model.request.NewsDetailRequest;


import io.reactivex.Observable;
import x.http.result.IHttpResult;
import x.lib.http.rx.BaseObserver;
import x.lib.ui.mvp.IBasePresenter;
import x.lib.ui.mvp.IBaseView;

/**
 * description: NewsDetailContract
 * author: lijun
 * date: 17/12/18 19:53
 */

public class NewsDetailContract {

    interface DetailView extends IBaseView {
        void setDetail(NewsDetail detail);
        void saveDb(String msg);
    }

    interface DetailModel {
        Observable<NewsDetail> getNewsDetail(NewsDetailRequest request);
        void mSaveDb(String url, String title, String id, IHttpResult<String> observer);
    }

    interface DetailPresenter extends IBasePresenter<DetailView> {
        void getDetail(NewsDetailRequest request);
        void saveDb(String url,String title,String id);
    }
}
