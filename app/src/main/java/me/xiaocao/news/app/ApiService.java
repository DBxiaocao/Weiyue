package me.xiaocao.news.app;

import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.xiaocao.news.model.Jiemian;
import me.xiaocao.news.model.Video;
import me.xiaocao.news.model.Zhihu;
import me.xiaocao.news.model.ZhihuDetail;
import me.xiaocao.news.model.ZhihuResult;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import x.lib.http.bean.HttpResult;

/**
 * description: ApiService
 * author: lijun
 * date: 18/1/3 19:58
 */

public interface ApiService {
    @GET
    Observable<ZhihuResult<List<Zhihu>>> getZhihuList(@Url String url, @QueryMap Map<String, String> map);

    @GET
    Observable<ZhihuDetail> getZhihuDetail(@Url String url, @QueryMap Map<String, String> map);

    @GET
    Observable<HttpResult<Jiemian>> getJiemianList(@Url String url);

    @GET
    Observable<ResponseBody> getVideoList(@Url String url, @QueryMap Map<String, String> map);

}
