package x.lib.jsoup;


import android.support.annotation.IntDef;
import android.util.ArrayMap;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * description: RxJsoupNetWork
 * author: xiaocao
 * date: 17/7/23 上午1:06
 */

public class RxJsoupNetWork {
    public static final String TAG = "RxJsoupNetWork";
    private final ArrayMap<Object, Disposable> arrayMap;

    public static final int GET = 1;
    public static final int POST = 2;

    @IntDef({GET,
            POST})
    @Retention(RetentionPolicy.SOURCE)
    @interface Method {
    }

    private String jsoupHeader ="Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
//    private String jsoupHeader ="Mozilla/5.0 (Linux; Android 6.0.1; MI 4LTE Build/MMB29M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/55.0.2883.91 Mobile Safari/537.36";
    private Connection connection = null;

    private RxJsoupNetWork() {
        arrayMap = new ArrayMap<>();
    }

    public <T> DisposableObserver<T> getApi(@NonNull String url, RxJsoupNetWorkListener<T> listener) {
        return getApi(TAG, url, GET, listener);
    }

    public <T> DisposableObserver<T> getApi(@NonNull Object tag, @NonNull String url, RxJsoupNetWorkListener<T> listener) {
        return getApi(tag, url, GET, listener);
    }

    public <T> DisposableObserver<T> postApi(@NonNull String url, RxJsoupNetWorkListener<T> listener) {
        return getApi(TAG, url, POST, listener);
    }

    public <T> DisposableObserver<T> postApi(@NonNull Object tag, @NonNull String url, RxJsoupNetWorkListener<T> listener) {
        return getApi(tag, url, POST, listener);
    }

    public static Document getT(@NonNull String url) throws IOException {
        return getT(GET, url);
    }

    public static Document getT(@Method int type, @NonNull String url) throws IOException {
        switch (type) {
            case POST:
                return getInstance().postDocument(url);
            case GET:
                return getInstance().getDocument(url);
        }
        return getInstance().getDocument(url);
    }

    public <T> DisposableObserver<T> getApi(@NonNull Observable<T> observable, DisposableObserver<T> listener) {
        return getApi(TAG, observable, listener);
    }

    public <T> DisposableObserver<T> getApi(@NonNull Object tag, @NonNull Observable<T> observable, DisposableObserver<T> listener) {
        if (listener == null) {
            throw new NullPointerException("listener is null");
        }
        DisposableObserver<T> disposableObserver =
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(listener);
        arrayMap.put(tag, disposableObserver);
        return disposableObserver;
    }


    public <T> DisposableObserver<T> getApi(@NonNull Object tag,
                                            @NonNull final String url,
                                            @Method final int type,
                                            final RxJsoupNetWorkListener<T> listener) {
        if (listener == null) {
            throw new NullPointerException("listener is null");
        }
        Log.d(TAG, "getApi: "+url);
        listener.onNetWorkStart();
        DisposableObserver<T> disposableObserver = Observable
                .create(
                        new ObservableOnSubscribe<T>() {

                            @Override
                            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                                Document document = null;
                                switch (type) {
                                    case GET:
                                        document = getDocument(url);
                                        break;
                                    case POST:
                                        document = postDocument(url);
                                        break;
                                }
                                e.onNext(listener.getT(document));
                                e.onComplete();
                            }
                        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<T>() {
                    @Override
                    public void onNext(T t) {
                        listener.onNetWorkSuccess(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onNetWorkError(e);
                    }

                    @Override
                    public void onComplete() {
                        listener.onNetWorkComplete();
                    }
                });
        arrayMap.put(tag, disposableObserver);
        return disposableObserver;
    }

    public void cancel(@NonNull Object tag) {
        Disposable disposable = arrayMap.get(tag);
        if (!isEmpty(disposable) && !disposable.isDisposed()) {
            disposable.dispose();
            arrayMap.remove(tag);
        }
    }

    public void cancelAll() {
        for (Map.Entry<Object, Disposable> disposableEntry : arrayMap.entrySet()) {
            cancel(disposableEntry.getKey());
        }
    }

    public RxJsoupNetWork setConnection(@NonNull Connection connection) {
        this.connection = connection;
        return this;
    }

    public RxJsoupNetWork setUserAgent(@NonNull String userAgent) {
        this.jsoupHeader = userAgent;
        return this;
    }

    public static RxJsoupNetWork getInstance() {
        return RxJsoupNetWorkHolder.rxJsoupNetWork;
    }

    private Document getDocument(@NonNull String url) throws IOException {
        if (connection != null) {
            return connection.get();
        }
        return connect(url).get();
    }


    private Document postDocument(@NonNull String url) throws IOException {
        if (connection != null) {
            return connection.post();
        }
        return connect(url).post();
    }

    private Connection connect(@NonNull String url) {
        return Jsoup.connect(url.trim()).header("User-Agent", jsoupHeader);
    }

    private static final class RxJsoupNetWorkHolder {
        private static final RxJsoupNetWork rxJsoupNetWork = new RxJsoupNetWork();
    }

    public static boolean isEmpty(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }
}
