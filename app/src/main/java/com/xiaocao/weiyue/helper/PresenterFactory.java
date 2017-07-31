package com.xiaocao.weiyue.helper;

import com.xiaocao.weiyue.presenter.INewsPresenter;
import com.xiaocao.weiyue.presenter.IPicPresenter;
import com.xiaocao.weiyue.presenter.IVideoPresenter;

/**
 * className: PresenterFactory
 * author: lijun
 * date: 17/6/30 11:10
 */

public class PresenterFactory {
    public static INewsPresenter getNewsPresenter() {
        return new NewsHelper();
    }

    public static IVideoPresenter getVideoListPresenter() {
        return new VideoHelper();
    }

    public static IPicPresenter getListPicPresenter() {
        return new PicHelper();
    }
}
