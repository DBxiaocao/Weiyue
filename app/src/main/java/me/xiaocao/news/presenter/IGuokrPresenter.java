package me.xiaocao.news.presenter;

import me.xiaocao.news.model.request.GuokrDetailRequest;
import me.xiaocao.news.model.request.GuokrListRequest;

/**
 * description: IZhiHuPresenter
 * author: lijun
 * date: 17/9/8 21:37
 */

public interface IGuokrPresenter {
    void getGuokrList(GuokrListRequest listRequest);
    void getGuokrDetail(GuokrDetailRequest request);
}
