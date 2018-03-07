package me.xiaocao.news.presenter;

import me.xiaocao.news.model.request.VideoRequest;

/**
 * description: IVideoPresenter
 * author: lijun
 * date: 17/8/27 14:32
 */

public interface IVideoPresenter {
    void getVideoList(VideoRequest request);
}
