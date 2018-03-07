package me.xiaocao.news.ui.zhihu;

import java.util.List;

import io.reactivex.Observable;
import me.xiaocao.news.model.Zhihu;
import me.xiaocao.news.model.request.ZhihuListRequest;
import x.lib.ui.mvp.IBasePresenter;
import x.lib.ui.mvp.IBaseView;

/**
 * description: ZhiHuContract
 * author: lijun
 * date: 18/1/3 19:50
 */

public class ZhiHuContract {
    interface IView extends IBaseView {
        void onRefreshData(List<Zhihu> list);

        void onLoadData(List<Zhihu> list);
    }

    interface IModel {
        Observable<List<Zhihu>> getZhihuList(ZhihuListRequest request);
    }

    interface IPresenter extends IBasePresenter<IView> {
        void refreshData(ZhihuListRequest request);

        void loadData(ZhihuListRequest request);
    }
}
