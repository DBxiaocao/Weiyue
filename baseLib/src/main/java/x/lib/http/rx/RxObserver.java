package x.lib.http.rx;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import x.lib.BaseApplication;
import x.lib.http.bean.HttpResult;
import x.lib.http.exception.ApiException;
import x.lib.http.exception.ExceptionEngine;
import x.lib.utils.NetUtils;

/**
 * description: RxObserver
 * author: lijun
 * date: 17/12/8 21:36
 */

public abstract class RxObserver<T> implements Observer<HttpResult<T>> {

//    private String mTag;//请求标识
//
//
//    public BaseObserver(String tag) {
//        this.mTag = tag;
//    }

    @Override
    public void onSubscribe(Disposable d) {
        onStart(d);
    }

    @Override
    public void onNext(HttpResult<T> value) {
        onDestroy();
        if (value.isSuccess()) {
            T t = value.getResult();
            onSuccess(t);
        } else {
            onError(new ApiException(value.getState(), value.getDescription()));
        }
    }

    @Override
    public void onError(Throwable e) {
        onDestroy();
        if (!NetUtils.isConnected(BaseApplication.getInstance())) {
            onError(new ApiException(ExceptionEngine.CODE_NETWORK, "网络未连接，请检查后再试"));
        } else {
            if (e instanceof ApiException) {
                onError((ApiException) e);
            } else {
                onError(new ApiException(e, ExceptionEngine.UN_KNOWN_ERROR));
            }
        }
    }

    @Override
    public void onComplete() {
    }


    protected abstract void onStart(Disposable disposable);

    protected abstract void onSuccess(T t);

    protected abstract void onError(ApiException e);

    protected abstract void onDestroy();

}

