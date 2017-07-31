package x.http.request;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.Request;
import x.http.util.RequestUtil;


/**
 * className: PostRequest
 * author: lijun
 * date: 17/6/29 15:39
 */

public abstract class PostRequest extends ARequest implements OkRequestEntity{
//    public String token;
//
//    public PostRequest(String token) {
//        this.token = token;
//    }

    @Override
    public Request getRequest() {
        return new Request.Builder().url(url()).post(RequestUtil.getUtil().getOkRequestBody(this)).build();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public Map<String, String> params() {
        return RequestUtil.getUtil().genMapParams(this);
    }

    @Override
    public RequestUtil.RequestMethod method() {
        return RequestUtil.RequestMethod.POST;
    }
}
