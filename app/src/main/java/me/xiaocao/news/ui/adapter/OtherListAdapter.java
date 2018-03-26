package me.xiaocao.news.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.xiaocao.news.R;
import me.xiaocao.news.model.Jiemian;
import x.lib.utils.DateUtils;
import x.lib.utils.GlideUtils;
import x.lib.utils.StringUtils;

/**
 * description: OtherListAdapter
 * author: lijun
 * date: 18/1/4 20:53
 */

public class OtherListAdapter extends BaseMultiItemQuickAdapter<Jiemian.ListEntityX, BaseViewHolder> {

    private Context context;

    public OtherListAdapter(List<Jiemian.ListEntityX> data, Context context) {
        super(data);
        this.context = context;
        addItemType(Jiemian.ListEntityX.Type_show_img_top, R.layout.list_item_jiemian_one);
        addItemType(Jiemian.ListEntityX.Type_show_img_right, R.layout.list_item_jiemian_two);
    }

    @Override
    protected void convert(BaseViewHolder helper, Jiemian.ListEntityX item) {
        switch (item.getItemType()) {
            case Jiemian.ListEntityX.Type_show_img_top:
                GlideUtils.loadImageView(context, item.getArticle().getAr_image(), (ImageView) helper.getView(R.id.ivNews));
                helper.setText(R.id.tvNewsTitle, item.getArticle().getAr_tl());
                helper.setText(R.id.tvNewsSource,item.getArticle().getAr_an());
                helper.setText(R.id.tvNewsTime, DateUtils.timeStampToStr(Long.valueOf(item.getArticle().getAr_pt())));
                break;
            case Jiemian.ListEntityX.Type_show_img_right:
                ImageView arImage=helper.getView(R.id.ivNews);
                if (StringUtils.isEmpty(item.getArticle().getAr_image())){
                    arImage.setVisibility(View.GONE);
                }else {
                    GlideUtils.loadImageView(context, item.getArticle().getAr_image(), arImage);
                    arImage.setVisibility(View.VISIBLE);
                }
                helper.setText(R.id.tvNewsTitle, item.getArticle().getAr_tl());
                helper.setText(R.id.tvNewsSource,item.getArticle().getAr_an());
                helper.setText(R.id.tvNewsTime, DateUtils.timeStampToStr(Long.valueOf(item.getArticle().getAr_pt())));
                break;
        }
    }
}
