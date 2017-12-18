package com.xiaocao.weiyue.ui.home.detail;

import com.xiaocao.weiyue.Constants;
import com.xiaocao.weiyue.dao.CollectionDao;
import com.xiaocao.weiyue.model.CollectionVo;
import com.xiaocao.weiyue.model.NewsDetail;
import com.xiaocao.weiyue.model.request.NewsDetailRequest;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import x.http.HttpUtils;
import x.http.result.IHttpResult;
import x.http.result.ResponseResult;
import x.http.util.JsonUtil;
import x.lib.http.rx.BaseObserver;
import x.lib.http.rx.RxSchedulers;
import x.lib.utils.ToastUtils;

/**
 * description: NewsDetailModel
 * author: lijun
 * date: 17/12/18 19:56
 */

public class NewsDetailModel implements NewsDetailContract.DetailModel {
    @Override
    public Observable<NewsDetail> getNewsDetail(final NewsDetailRequest request) {
        return HttpUtils.async(request).map(new Function<ResponseResult, NewsDetail>() {
            @Override
            public NewsDetail apply(@NonNull ResponseResult responseResult) throws Exception {
                if (responseResult.success) {
                    Map<String, NewsDetail> map = JsonUtil.stringToCollect(responseResult.result);
                    return JsonUtil.toObject(JsonUtil.GsonString(map.get(request.id)), NewsDetail.class);
                } else {
                    return new NewsDetail();
                }
            }
        }).compose(RxSchedulers.<NewsDetail>composeObs());
    }

    @Override
    public void mSaveDb(String url, String title, String id, IHttpResult<String> httpResult) {
        CollectionVo dbVo = CollectionDao.queryImgUrl(url);
        if (null != dbVo && dbVo.getImgUrl().equals(url)) {
            CollectionDao.deleteChannel(dbVo.getId());
            httpResult.onSuccess("已取消收藏");
        } else {
            CollectionVo vo = new CollectionVo();
            vo.setType(CollectionVo.TYPE_News);
            vo.setImgUrl(url);
            vo.setTitle(title);
            vo.setNewsId(id);
            vo.setId(CollectionDao.queryAll().size() + 1);
            CollectionDao.insert(vo);
            httpResult.onSuccess("已添加收藏");
        }
    }
}
