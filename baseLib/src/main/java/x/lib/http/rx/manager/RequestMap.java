package x.lib.http.rx.manager;

import io.reactivex.disposables.Disposable;

/**
 * description: RequestMap
 * author: lijun
 * date: 17/12/27 11:23
 */

public interface RequestMap<T> {
    /**
     * 添加
     *
     * @param tag
     * @param disposable
     */
    void add(T tag, Disposable disposable);

    /**
     * 移除
     *
     * @param tag
     */
    void remove(T tag);

    /**
     * 取消
     *
     * @param tag
     */
    void cancel(T tag);
}
