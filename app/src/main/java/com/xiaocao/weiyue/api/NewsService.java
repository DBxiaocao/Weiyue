package com.xiaocao.weiyue.api;


import com.xiaocao.weiyue.model.News;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * description: NewsService
 * author: lijun
 * date: 17/12/18 16:56
 */

public interface NewsService {
    @GET("nc/article/article/{id}/{limit}-20.html")
    Observable<Map<String, List<News>>> getNewss(@Path("id") String id,
                                                @Path("limit") int limit);
    @GET("nc/article/article/{id}/{limit}-20.html")
    Observable<String> getNews(@Path("id") String id,
                                                @Path("limit") int limit);
}
