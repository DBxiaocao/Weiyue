package x.lib.ui.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * description: BasePersenterImpl
 * author: lijun
 * date: 17/11/29 上午10:43
 */
public abstract class BasePresenterImpl<V extends IBaseView> implements IBasePresenter<V> {

    private CompositeDisposable mCompositeDisposable;
    private Reference<V> mvpView;//弱引用

    @Override
    public void createView(V view) {
        this.mvpView = new WeakReference<>(view);
    }

    @Override
    public void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        if (mvpView != null) {
            mvpView.clear();
            mvpView = null;
        }
    }

    @Override
    public V getView() {
        return mvpView.get();
    }

    @Override
    public void addDisposable(Disposable subscription) {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * 判断 view是否为空
     *
     * @return
     */
    public boolean isAttachView() {
        return mvpView != null && mvpView.get() != null;
    }

}