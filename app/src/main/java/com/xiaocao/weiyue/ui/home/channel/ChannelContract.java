package com.xiaocao.weiyue.ui.home.channel;

import com.xiaocao.weiyue.model.ChannelVo;

import java.util.List;

import x.lib.ui.mvp.IBasePresenter;
import x.lib.ui.mvp.IBaseView;

/**
 * description: ChannelContract
 * author: lijun
 * date: 17/12/18 20:21
 */

public class ChannelContract {
    interface IView extends IBaseView {
        void setTopList(List<ChannelVo> list);

        void setBottom(List<ChannelVo> list);
    }

    interface IPresenter extends IBasePresenter<IView> {
        void getTopList();

        void getBottom();
    }

}
