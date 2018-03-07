package me.xiaocao.news.presenter;

import me.xiaocao.news.model.request.ZhiHuDetailRequest;
import me.xiaocao.news.model.request.ZhihuListRequest;

/**
 * description: IZhiHuPresenter
 * author: lijun
 * date: 17/9/8 21:37
 */

public interface IZhiHuPresenter {
    void getZhiHuList(ZhihuListRequest listRequest);
    void getZhiDetail(ZhiHuDetailRequest detailRequest);
}
