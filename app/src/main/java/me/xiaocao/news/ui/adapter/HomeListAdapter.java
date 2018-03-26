package me.xiaocao.news.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import me.xiaocao.news.R;
import me.xiaocao.news.app.Constants;
import me.xiaocao.news.model.Jiemian;
import me.xiaocao.news.ui.jiemian.channel.OtherListActivity;
import x.lib.utils.DateUtils;
import x.lib.utils.GlideUtils;
import x.lib.utils.ToastUtils;
import x.lib.view.marquee.MarqueeBean;
import x.lib.view.marquee.MarqueeView;

/**
 * description: HomeListAdapter
 * author: lijun
 * date: 18/1/4 20:49
 */

public class HomeListAdapter extends BaseMultiItemQuickAdapter<Jiemian.ListEntityX, BaseViewHolder> {
    private Context context;

    public HomeListAdapter(List<Jiemian.ListEntityX> data, Context context) {
        super(data);
        this.context = context;
        addItemType(Jiemian.ListEntityX.Type_kuaixun_tpl, R.layout.list_item_home_type_1);
        addItemType(Jiemian.ListEntityX.Type_xiaotu, R.layout.list_item_home_type_2);
        addItemType(Jiemian.ListEntityX.Type_pindao_hengla_tpl, R.layout.list_item_home_type_3);
        addItemType(Jiemian.ListEntityX.Type_banner_img, R.layout.list_item_home_type_4);
        addItemType(Jiemian.ListEntityX.Type_h5_hengla_tpl, R.layout.list_item_home_type_5);
        addItemType(Jiemian.ListEntityX.Type_zhuanti_lunbo_tpl, R.layout.list_item_home_type_6);
        addItemType(Jiemian.ListEntityX.Type_shipin_hengla_tpl, R.layout.list_item_home_type_7);
    }

    @Override
    protected void convert(BaseViewHolder helper, Jiemian.ListEntityX item) {
        switch (item.getItemType()) {
            case Jiemian.ListEntityX.Type_kuaixun_tpl:
                MarqueeView ivMarquee = helper.getView(R.id.ivMarquee);
                final List<MarqueeBean> marqueeBeans = new ArrayList<>();
                for (Jiemian.ListEntityX.ListEntity listEntity : item.getList()) {
                    MarqueeBean bean = new MarqueeBean();
                    bean.setTitle(listEntity.getArticle().getAr_tl());
                    bean.setImgUrl(listEntity.getArticle().getAr_id());
                    marqueeBeans.add(bean);
                }
                ivMarquee.startWithList(marqueeBeans);
                ivMarquee.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, View textView) {
                        ToastUtils.showShort(context,marqueeBeans.get(position).getImgUrl());
                    }
                });
                helper.setText(R.id.kuaixun, item.getChannel().getTitle());
                break;
            case Jiemian.ListEntityX.Type_xiaotu:
                GlideUtils.loadImageView(context, item.getArticle().getAr_image(), (ImageView) helper.getView(R.id.ivNews));
                helper.setText(R.id.tvNewsTitle, item.getArticle().getAr_tl());
                helper.setText(R.id.tvNewsSource, item.getArticle().getAr_an());
                helper.setText(R.id.tvNewsTime, DateUtils.timeStampToStr(Long.valueOf(item.getArticle().getAr_pt())));
                break;
            case Jiemian.ListEntityX.Type_pindao_hengla_tpl:
                helper.setText(R.id.tvChannel, item.getChannel().getTitle());
                RecyclerView recycler = helper.getView(R.id.recycler);
                recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                final BaseListAdapter<Jiemian.ListEntityX.ListEntity> pindaoAdapter = new BaseListAdapter<>(R.layout.grid_item_news_type, item.getList());
                pindaoAdapter.setOnCallBackData(new BaseListAdapter.OnCallBackData<Jiemian.ListEntityX.ListEntity>() {
                    @Override
                    public void convertView(BaseViewHolder helper, Jiemian.ListEntityX.ListEntity item) {
                        GlideUtils.loadImageView(context, item.getArticle().getAr_image(), (ImageView) helper.getView(R.id.ivNews));
                        helper.setText(R.id.tvNewsTitle, item.getArticle().getAr_tl());
                        helper.setText(R.id.tvNewsId, item.getArticle().getAr_channel_name());
                    }
                });
                pindaoAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent=new Intent();
                        intent.setClass(context, OtherListActivity.class);
                        intent.putExtra(Constants.JIEMIAN_NEWS_ID,pindaoAdapter.getItem(position).getArticle().getAr_channel_url());
                        intent.putExtra(Constants.JIEMIAN_NEWS_TITLE,pindaoAdapter.getItem(position).getArticle().getAr_channel_name());
                        context.startActivity(intent);
                    }
                });
                recycler.setAdapter(pindaoAdapter);
                break;
            case Jiemian.ListEntityX.Type_banner_img:
                GlideUtils.loadImageView(context, item.getAds().get(0).getAd_img(), (ImageView) helper.getView(R.id.ivNews));
                helper.setText(R.id.tvNewsTitle, item.getAds().get(0).getAd_name());
                break;
            case Jiemian.ListEntityX.Type_h5_hengla_tpl:
//                helper.setText(R.id.tvAdsName, item.getChannel().getTitle());
                break;
            case Jiemian.ListEntityX.Type_zhuanti_lunbo_tpl:
//                helper.setText(R.id.tvZhuanti, item.getList().get(0).getSpecial().getTl());
                break;
            case Jiemian.ListEntityX.Type_shipin_hengla_tpl:
                helper.setText(R.id.tvVideo, item.getChannel().getTitle());
                RecyclerView videoRecycler = helper.getView(R.id.recycler);
                videoRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                final BaseListAdapter<Jiemian.ListEntityX.ListEntity> videAdapter = new BaseListAdapter<>(R.layout.grid_item_news_type, item.getList());
                videAdapter.setOnCallBackData(new BaseListAdapter.OnCallBackData<Jiemian.ListEntityX.ListEntity>() {
                    @Override
                    public void convertView(BaseViewHolder helper, Jiemian.ListEntityX.ListEntity item) {
                        GlideUtils.loadImageView(context, item.getVideo().getV_image(), (ImageView) helper.getView(R.id.ivNews));
                        helper.setText(R.id.tvNewsTitle, item.getVideo().getV_tl());
                        helper.setText(R.id.tvNewsId, item.getAuthor().getName());
                    }
                });
                videAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        ToastUtils.showShort(context,videAdapter.getItem(position).getAuthor().getUid());
                    }
                });
                videoRecycler.setAdapter(videAdapter);
                break;
            default:

                break;
        }
    }

}
