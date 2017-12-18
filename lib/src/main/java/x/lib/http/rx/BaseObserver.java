package x.lib.http.rx;


import com.google.gson.JsonSyntaxException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import x.lib.BaseApplication;
import x.lib.http.bean.HttpResult;
import x.lib.utils.NetUtils;

/**
 * description: BaseObserver
 * author: lijun
 * date: 17/12/8 21:36
 */

public abstract class BaseObserver<T> implements Observer<HttpResult<T>> {
    public final static int CODE_NETWORK = 21;//无网络
    public final static int CODE_JSON = 22;//json解析错误
    public final static int CODE_ERR = 23;//其它错误；

    @Override
    public void onSubscribe(Disposable d) {
        onUiStart(d);
    }

    @Override
    public void onNext(HttpResult<T> value) {
        if (value.isSuccess()) {
            T t = value.getResult();
            onSuccess(t);
        } else {
            onError(value.getState(), value.getDescription());
        }
    }

    @Override
    public void onError(Throwable e) {
        if (!NetUtils.isConnected(BaseApplication.getInstance())) {
            onError(CODE_NETWORK, "网络未连接，请检查后再试");
        } else {
            if (e instanceof JsonSyntaxException) {
                onError(CODE_JSON, "解析错误，请稍后再试");
            } else {
                onError(CODE_ERR, e.getMessage());
            }
        }
    }

    @Override
    public void onComplete() {
        onUiEnd();
    }


    protected abstract void onSuccess(T t);

    protected abstract void onError(int code, String msg);

    protected abstract void onUiStart(Disposable disposable);

    protected abstract void onUiEnd();

}

