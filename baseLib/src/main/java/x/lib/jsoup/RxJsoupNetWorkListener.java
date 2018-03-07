package x.lib.jsoup;

import org.jsoup.nodes.Document;

/**
 * className: RxJsoupNetWorkListener
 * author: lijun
 * date: 17/6/1 19:57
 */

public interface RxJsoupNetWorkListener<T> {
    void onNetWorkStart();

    void onNetWorkError(Throwable e);

    void onNetWorkComplete();

    void onNetWorkSuccess(T t);

    T getT(Document document);
}