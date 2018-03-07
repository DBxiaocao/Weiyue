package me.xiaocao.news.ui.zhihu;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.xiaocao.news.app.Api;
import me.xiaocao.news.app.ApiService;
import me.xiaocao.news.model.Zhihu;
import me.xiaocao.news.model.ZhihuResult;
import me.xiaocao.news.model.request.ZhihuListRequest;
import x.lib.http.retrofit.RetrofitUtil;

/**
 * description: ZhiHuModelImpl
 * author: lijun
 * date: 18/1/3 19:55
 */

public class ZhiHuModelImpl implements ZhiHuContract.IModel {
    @Override
    public Observable<List<Zhihu>> getZhihuList(ZhihuListRequest request) {
        return RetrofitUtil.getInstance()
                .retrofit(Api.ZHIHU_HOST)
                .create(ApiService.class)
                .getZhihuList(request.url(), request.params())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ZhihuResult<List<Zhihu>>, List<Zhihu>>() {
                    @Override
                    public List<Zhihu> apply(ZhihuResult<List<Zhihu>> zhihu) throws Exception {
                        if (null != zhihu && null != zhihu.getStories() && zhihu.getStories().size() > 0) {
                            return zhihu.getStories();
                        } else {
                            return new ArrayList<>();
                        }
                    }
                });
    }

}
