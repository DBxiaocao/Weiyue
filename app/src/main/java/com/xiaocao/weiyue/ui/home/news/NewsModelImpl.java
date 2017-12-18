package com.xiaocao.weiyue.ui.home.news;

import com.xiaocao.weiyue.model.News;
import com.xiaocao.weiyue.model.request.NewsRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import x.http.HttpUtils;
import x.http.result.ResponseResult;
import x.http.util.JsonUtil;
import x.lib.http.rx.RxSchedulers;

/**
 * description: NewsModelImpl
 * author: lijun
 * date: 17/12/18 17:03
 */

public class NewsModelImpl implements NewsContract.NewsModel {


    @Override
    public Observable<List<News>> getNews(final NewsRequest request) {
        return HttpUtils.async(request)
                .map(new Function<ResponseResult, List<News>>() {
                    @Override
                    public List<News> apply(@NonNull ResponseResult responseResult) throws Exception {
                        if (responseResult.success) {
                            Map<String, List<News>> map = JsonUtil.stringToCollect(responseResult.result);
                            return JsonUtil.toObjects(map.get(request.id).toString(), News.class);
                        } else {
                            return new ArrayList<>();
                        }
                    }
                })
                .compose(RxSchedulers.<List<News>>composeObs())
                .distinct();
    }
}
