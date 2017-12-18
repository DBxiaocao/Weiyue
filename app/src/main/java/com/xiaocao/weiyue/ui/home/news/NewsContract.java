package com.xiaocao.weiyue.ui.home.news;

import com.xiaocao.weiyue.model.News;
import com.xiaocao.weiyue.model.request.NewsRequest;

import java.util.List;

import io.reactivex.Observable;
import x.lib.ui.mvp.IBasePresenter;
import x.lib.ui.mvp.IBaseView;

/**
 * description: NewsContract
 * author: lijun
 * date: 17/12/18 16:52
 */

public class NewsContract {
    interface NewsView extends IBaseView {
        void setNewsRefresh(List<News> list);
        void setNewsLoad(List<News> list);
    }

    interface NewsModel {
        Observable<List<News>> getNews(NewsRequest request);
    }

    interface NewsPresenter extends IBasePresenter<NewsView> {
        void getNews(NewsRequest request);
    }
}
