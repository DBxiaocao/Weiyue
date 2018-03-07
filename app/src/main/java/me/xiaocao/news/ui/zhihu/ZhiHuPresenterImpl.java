package me.xiaocao.news.ui.zhihu;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.xiaocao.news.model.Zhihu;
import me.xiaocao.news.model.request.ZhihuListRequest;
import x.lib.ui.mvp.BasePresenterImpl;
import x.lib.utils.LogUtil;

/**
 * description: ZhiHuPresenterImpl
 * author: lijun
 * date: 18/1/3 19:56
 */

public class ZhiHuPresenterImpl extends BasePresenterImpl<ZhiHuContract.IView> implements ZhiHuContract.IPresenter {

    private ZhiHuContract.IModel iModel;

    public ZhiHuPresenterImpl() {
        iModel = new ZhiHuModelImpl();
    }

    @Override
    public void refreshData(ZhihuListRequest request) {
        iModel.getZhihuList(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Zhihu>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(List<Zhihu> list) {
                        if (isAttachView())
                            getView().onRefreshData(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (isAttachView())
                            getView().onErrMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadData(ZhihuListRequest request) {
        iModel.getZhihuList(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Zhihu>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(List<Zhihu> list) {
                        if (isAttachView())
                            getView().onLoadData(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isAttachView())
                            getView().onErrMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
