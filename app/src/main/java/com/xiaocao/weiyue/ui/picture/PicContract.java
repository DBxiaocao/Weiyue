package com.xiaocao.weiyue.ui.picture;

import com.xiaocao.weiyue.model.Pics;
import com.xiaocao.weiyue.model.request.PicRequest;

import java.util.List;

import x.lib.ui.mvp.IBasePresenter;
import x.lib.ui.mvp.IBaseView;

/**
 * description: PicContract
 * author: lijun
 * date: 17/12/18 21:06
 */

public class PicContract {
    interface IView extends IBaseView{
        void picReFresh(List<Pics> list);
        void picLoad(List<Pics> list);
    }

    interface IPresenter extends IBasePresenter<IView>{
        void getPicList(PicRequest request);
    }
}
