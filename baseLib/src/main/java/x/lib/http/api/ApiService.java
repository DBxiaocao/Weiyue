package x.lib.http.api;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * description: ApiService
 * author: lijun
 * date: 17/12/10 20:43
 */

public interface ApiService {
//    @FormUrlEncoded
//    @POST
//    Observable<HttpResult> post(@Url String url, @FieldMap Map<String, String> params);
//
//    @GET
//    Observable<HttpResult> get(@Url String url, @QueryMap Map<String, String> params);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
