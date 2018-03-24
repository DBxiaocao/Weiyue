package me.xiaocao.news.ui.jiemian.channel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.xiaocao.news.app.Api;
import me.xiaocao.news.app.ApiService;
import me.xiaocao.news.model.Jiemian;
import me.xiaocao.news.model.request.HomeListRequest;
import me.xiaocao.news.model.request.OtherListRequest;
import x.lib.http.bean.HttpResult;
import x.lib.http.request.get.GetRequest;
import x.lib.http.retrofit.RetrofitUtil;
import x.lib.http.rx.RxSchedulers;

/**
 * description: HomeListModel
 * author: lijun
 * date: 18/1/4 20:20
 */

public class HomeListModel implements HomeListContract.IModel {
    private final static String kuaixun_tpl = "kuaixun_tpl";
    private final static String xiaotu = "xiaotu";
    private final static String pindao_hengla_tpl = "pindao_hengla_tpl";
    private final static String type_ads = "ads";
    private final static String banner_img = "banner_img";
    private final static String h5_hengla_tpl = "h5_hengla_tpl";
    private final static String zhuanti_lunbo_tpl = "zhuanti_lunbo_tpl";
    private final static String show_img_top = "show_img_top";
    private final static String show_img_top_intro = "show_img_top_intro";
    private final static String show_img_right = "show_img_right";
    private final static String shipin_hengla_tpl = "shipin_hengla_tpl";

    @Override
    public Observable<Jiemian> getHomelist(final GetRequest request) {
        return RetrofitUtil.getInstance()
                .retrofit(Api.JIEMIAN_HOST)
                .create(ApiService.class)
                .getJiemianList(request.url())
                .map(new Function<HttpResult<Jiemian>, Jiemian>() {
                    @Override
                    public Jiemian apply(HttpResult<Jiemian> jiemian) throws Exception {
                        List<Jiemian.CarouselEntity> bannerlist = new ArrayList<>();
                        if (null != jiemian.getResult().getCarousel() && jiemian.getResult().getCarousel().size() > 0) {
                            for (Jiemian.CarouselEntity entity : jiemian.getResult().getCarousel()) {
                                if (null != entity.getType()
                                        && entity.getType().equals("article")) {//只取type 为article的banner
                                    bannerlist.add(entity);
                                }
                            }
                            jiemian.getResult().setCarousel(bannerlist);
                        }
                        if (request instanceof HomeListRequest) {
                            List<Jiemian.ListEntityX> list = new ArrayList<>();
                            for (Jiemian.ListEntityX entityX : jiemian.getResult().getList()) {
                                if (null != entityX.getI_show_tpl() && entityX.getI_show_tpl().equals(kuaixun_tpl)
                                        || null != entityX.getI_show_tpl() && entityX.getI_show_tpl().equals(xiaotu)
                                        || null != entityX.getI_show_tpl() && entityX.getI_show_tpl().equals(pindao_hengla_tpl)
                                        || null != entityX.getI_show_tpl() && entityX.getI_show_tpl().equals(shipin_hengla_tpl)) {//不取type 为ads的值,i_show_tpl为h5_hengla_tpl,zhuanti_lunbo_tpl
                                    list.add(entityX);
                                }
                            }
                            jiemian.getResult().setList(list);
                        }

                        if (request instanceof OtherListRequest){
                            List<Jiemian.ListEntityX> list=new ArrayList<>();
                            for (Jiemian.ListEntityX entityX : jiemian.getResult().getList()) {
                                if (null != entityX.getI_show_tpl() && entityX.getI_show_tpl().equals(show_img_top)
                                        || null != entityX.getI_show_tpl() && entityX.getI_show_tpl().equals(show_img_right)
                                        || null != entityX.getI_show_tpl() && entityX.getI_show_tpl().equals(show_img_top_intro)){
                                    list.add(entityX);
                                }
                            }
                            jiemian.getResult().setList(list);
                        }

                        return jiemian.getResult();
                    }
                })
                .compose(RxSchedulers.<Jiemian>composeObs())
                .distinct();
    }
}
