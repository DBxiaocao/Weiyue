package x.lib.http.rx;



import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import x.lib.http.bean.HttpResult;

/**
 * description: RxFunction
 * author: lijun
 * date: 17/12/8 22:34
 */

public class RxFunction<T> implements Function<HttpResult<T>, T> {

    @Override
    public T apply(@NonNull HttpResult<T> httpResult) throws Exception {

        int retCode = httpResult.getState();
        if (retCode != 200) {
            switch (retCode) {
                case 21001:
                    break;
                //  case 2111:
                //      throw ........
            }
        }
        return httpResult.getResult();
    }
}
