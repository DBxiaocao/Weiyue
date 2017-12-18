package com.xiaocao.weiyue.ui.home.detail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import x.lib.ui.BaseActivity;
import x.lib.ui.BaseMvpActivity;
import x.lib.ui.TitleView;
import x.lib.utils.GlideUtils;
import x.lib.utils.ToastUtils;

import com.xiaocao.weiyue.Constants;
import com.xiaocao.weiyue.R;
import com.xiaocao.weiyue.dao.CollectionDao;
import com.xiaocao.weiyue.helper.PresenterFactory;
import com.xiaocao.weiyue.model.CollectionVo;
import com.xiaocao.weiyue.model.NewsDetail;
import com.xiaocao.weiyue.model.event.NewsDetailEvent;
import com.xiaocao.weiyue.model.request.NewsDetailRequest;
import com.xiaocao.weiyue.presenter.INewsPresenter;
import com.xiaocao.weiyue.ui.home.detail.NewsDetailContract.DetailView;
import com.xiaocao.weiyue.utils.IntentUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;


/**
 * description: NewsDetailActivity
 * author: xiaocao
 * date: 17/7/5 下午6:00
 */
public class NewsDetailActivity extends BaseMvpActivity<NewsDetailPresenter> implements DetailView {


    @Bind(R.id.ivNewsImg)
    ImageView ivNewsImg;
    @Bind(R.id.tvNewsContent)
    TextView tvNewsContent;
    @Bind(R.id.tvNewsTitle)
    TextView tvNewsTitle;
    @Bind(R.id.toolbarLayout)
    CollapsingToolbarLayout toolbarLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setBack(activity);
    }


    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    protected void initInstance() {
        tvNewsTitle.setText(getIntent().getExtras().getString(Constants.NEWS_Title));
        toolbarLayout.setTitle(getIntent().getExtras().getString(Constants.NEWS_Title));
        GlideUtils.loadImageView(activity, getIntent().getExtras().getString(Constants.NEWS_Img), ivNewsImg);
        mPresenter.getDetail(new NewsDetailRequest().setId(getIntent().getExtras().getString(Constants.NEWS_ID)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        CollectionVo dbVo = CollectionDao.queryImgUrl(getIntent().getExtras().getString(Constants.NEWS_Img));
        if (null != dbVo && dbVo.getImgUrl().equals(getIntent().getExtras().getString(Constants.NEWS_Img))) {
            menu.getItem(0).setTitle("取消收藏");
        } else {
            menu.getItem(0).setTitle("添加收藏");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCollection:
//                CollectionVo dbVo = CollectionDao.queryImgUrl(getIntent().getExtras().getString(Constants.NEWS_Img));
//                if (null != dbVo && dbVo.getImgUrl().equals(getIntent().getExtras().getString(Constants.NEWS_Img))) {
//                    CollectionDao.deleteChannel(dbVo.getId());
//                    ToastUtils.showShort(activity, "已取消收藏");
//                } else {
//                    CollectionVo vo = new CollectionVo();
//                    vo.setType(CollectionVo.TYPE_News);
//                    vo.setImgUrl(getIntent().getExtras().getString(Constants.NEWS_Img));
//                    vo.setTitle(getIntent().getExtras().getString(Constants.NEWS_Title));
//                    vo.setNewsId(getIntent().getExtras().getString(Constants.NEWS_ID));
//                    vo.setId(CollectionDao.queryAll().size() + 1);
//                    CollectionDao.insert(vo);
//                    ToastUtils.showShort(activity, "已添加收藏");
//                }
                mPresenter.saveDb(getIntent().getExtras().getString(Constants.NEWS_Img)
                        , getIntent().getExtras().getString(Constants.NEWS_Title)
                        , getIntent().getExtras().getString(Constants.NEWS_ID));
                break;
            case R.id.menuShare:
                IntentUtil.shareText(activity, getIntent().getExtras().getString(Constants.NEWS_Title), getIntent().getExtras().getString(Constants.NEWS_HTML));
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onErrMsg(String errMsg) {
        ToastUtils.showShort(activity, errMsg);
    }

    @Override
    public void setDetail(NewsDetail detail) {
        tvNewsContent.setText(Html.fromHtml(detail.getBody()));
    }

    @Override
    public void saveDb(String msg) {
        ToastUtils.showShort(activity, msg);
        supportInvalidateOptionsMenu();
    }
}
