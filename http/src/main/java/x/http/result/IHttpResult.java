package x.http.result;

/**
 * description: IHttpResult
 * author: lijun
 * date: 17/12/18 20:14
 */

public interface IHttpResult<T> {
    void onSuccess(T data);
    void onErrMsg(String msg);
}
