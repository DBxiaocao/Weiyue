package me.xiaocao.news.ui.jiemian.channel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.xiaocao.news.model.Jiemian;
import me.xiaocao.news.model.request.HomeListRequest;
import x.lib.http.request.get.GetRequest;
import x.lib.ui.mvp.BasePresenterImpl;

/**
 * description: HomePresenter
 * author: lijun
 * date: 18/1/4 20:22
 */

public class HomeListPresenter extends BasePresenterImpl<HomeListContract.IView> implements HomeListContract.IPresenter {

    HomeListContract.IModel iModel;

    public HomeListPresenter() {
        iModel = new HomeListModel();
    }

    @Override
    public void loadList(GetRequest request) {
        addDisposable(iModel.getHomelist(request)
                .subscribe(new Consumer<Jiemian>() {
                    @Override
                    public void accept(Jiemian jiemian) throws Exception {
                        if (isAttachView()) {
                            if (null == jiemian) {
                                getView().onErrMsg("获取数据失败");
                            } else {
                                if (null != jiemian.getList() && jiemian.getList().size() > 0) {
                                    getView().getLoadList(jiemian.getList());
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (isAttachView())
                            getView().onErrMsg(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void refreshList(GetRequest request) {
        addDisposable(iModel.getHomelist(request)
                .subscribe(new Consumer<Jiemian>() {
                    @Override
                    public void accept(Jiemian jiemian) throws Exception {
                        if (isAttachView()) {
                            if (null == jiemian) {
                                getView().onErrMsg("获取数据失败");
                            } else {
                                if (null != jiemian.getCarousel() && jiemian.getCarousel().size()>0){
//                                    List<Jiemian.CarouselEntity> list=new ArrayList<>();
//                                    for (Jiemian.CarouselEntity entity : jiemian.getCarousel()) {
//                                        if (null !=entity.getType()
//                                                && entity.getType().equals("article")){//只取type 为article的banner
//                                            list.add(entity);
//                                        }
//                                    }
                                    getView().getCarousel(jiemian.getCarousel());
                                }
                                if (null != jiemian.getList() && jiemian.getList().size() > 0) {
                                    getView().getRefreshList(jiemian.getList());
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (isAttachView())
                            getView().onErrMsg(throwable.getMessage());
                    }
                }));
    }
}
