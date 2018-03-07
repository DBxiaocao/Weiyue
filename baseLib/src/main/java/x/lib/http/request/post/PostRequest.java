package x.lib.http.request.post;

import com.google.gson.Gson;

import java.util.Map;

import x.lib.http.request.ARequest;
import x.lib.utils.JsonUtil;


/**
 * className: PostRequest
 * author: lijun
 * date: 17/6/29 15:39
 */

public abstract class PostRequest extends ARequest {
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public Map<String, String> params() {
        return JsonUtil.GsonToMaps(toString());
    }

//    @Override
//    public RequestMethod method() {
//        return RequestMethod.POST;
//    }
}
