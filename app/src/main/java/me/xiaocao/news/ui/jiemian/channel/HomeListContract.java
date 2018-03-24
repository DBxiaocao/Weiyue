package me.xiaocao.news.ui.jiemian.channel;

import java.util.List;

import io.reactivex.Observable;
import me.xiaocao.news.model.Jiemian;
import me.xiaocao.news.model.request.HomeListRequest;
import x.lib.http.request.get.GetRequest;
import x.lib.ui.mvp.IBasePresenter;
import x.lib.ui.mvp.IBaseView;

/**
 * description: HomeListContract
 * author: lijun
 * date: 18/1/4 20:16
 */

public class HomeListContract {
    interface IView extends IBaseView {
        void getCarousel(List<Jiemian.CarouselEntity> list);

        void getRefreshList(List<Jiemian.ListEntityX> list);
        void getLoadList(List<Jiemian.ListEntityX> list);

        void goToDetail(String newsId,String newsTitle);
    }

    interface IModel {
        Observable<Jiemian> getHomelist(GetRequest request);
    }

    interface IPresenter extends IBasePresenter<IView> {
        void loadList(GetRequest request);

        void refreshList(GetRequest request);
    }
}
