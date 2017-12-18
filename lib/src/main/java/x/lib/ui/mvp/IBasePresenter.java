package x.lib.ui.mvp;

import io.reactivex.disposables.Disposable;

/**
 * description: BasePresenter
 * author: lijun
 * date: 17/11/29 10:25
 */

public interface IBasePresenter<V extends IBaseView> {
    void createView(V view);

    void onDestroy();

    V getView();

    void addDisposable(Disposable subscription);

}
