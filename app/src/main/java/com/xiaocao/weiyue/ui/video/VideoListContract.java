package com.xiaocao.weiyue.ui.video;

import com.xiaocao.weiyue.model.Video;
import com.xiaocao.weiyue.model.request.VideoRequest;

import java.util.List;

import x.lib.ui.mvp.IBasePresenter;
import x.lib.ui.mvp.IBaseView;

/**
 * description: VideoListContract
 * author: lijun
 * date: 17/12/18 20:30
 */

public class VideoListContract {
    interface IView extends IBaseView {
        void videoRefresh(List<Video> list);

        void videoLoad(List<Video> list);
    }

    interface IPresenter extends IBasePresenter<IView> {
        void getVideoList(VideoRequest request);
    }
}
