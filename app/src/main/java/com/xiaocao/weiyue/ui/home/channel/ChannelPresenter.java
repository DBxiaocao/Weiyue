package com.xiaocao.weiyue.ui.home.channel;

import com.xiaocao.weiyue.dao.ChannelDao;
import com.xiaocao.weiyue.model.ChannelVo;

import java.util.List;

import x.lib.ui.mvp.BasePresenterImpl;

/**
 * description: ChannelPresenter
 * author: lijun
 * date: 17/12/18 20:23
 */

public class ChannelPresenter extends BasePresenterImpl<ChannelContract.IView> implements ChannelContract.IPresenter {
    @Override
    public void getTopList() {
        if (isAttachView())
            getView().setTopList(ChannelDao.queryChannel(ChannelVo.TYPE_TOP));
    }

    @Override
    public void getBottom() {
        if (isAttachView())
            getView().setBottom(ChannelDao.queryChannel(ChannelVo.TYPE_BOTTOM));
    }
}
