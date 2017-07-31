package com.xiaocao.weiyue.presenter;

import com.xiaocao.weiyue.model.request.NewsDetailRequest;
import com.xiaocao.weiyue.model.request.NewsRequest;

/**
 * className: IHomeImpl
 * author: lijun
 * date: 17/6/30 11:07
 */

public interface INewsPresenter {
    void getNewsList(NewsRequest news);
    void getNewsDetail(NewsDetailRequest detailRequest);
}
