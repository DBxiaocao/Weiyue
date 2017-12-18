package com.xiaocao.weiyue.ui.home.news;

import com.xiaocao.weiyue.model.News;
import com.xiaocao.weiyue.model.request.NewsRequest;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import x.lib.ui.mvp.BasePresenterImpl;

/**
 * description: NewsPresenter
 * author: lijun
 * date: 17/12/18 16:59
 */

public class NewsPresenterImpl extends BasePresenterImpl<NewsContract.NewsView> implements NewsContract.NewsPresenter {

    private NewsContract.NewsModel model;

    public NewsPresenterImpl() {
        model = new NewsModelImpl();
    }

    @Override
    public void getNews(final NewsRequest request) {

        model.getNews(request).subscribe(new Observer<List<News>>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(List<News> news) {
                if (isAttachView() && news.size() > 0)
                    if (request.limit == 0)
                        getView().setNewsRefresh(news);
                    else
                        getView().setNewsLoad(news);

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
}
