package x.lib.http.request.get;

import com.google.gson.Gson;

import java.util.Map;

import x.lib.http.request.ARequest;
import x.lib.utils.JsonUtil;


/**
 * className: GetRequest
 * author: lijun
 * date: 17/6/29 15:35
 */

public abstract class GetRequest extends ARequest {

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public Map<String, String> params() {
        return JsonUtil.GsonToMaps(toString());
    }


}
