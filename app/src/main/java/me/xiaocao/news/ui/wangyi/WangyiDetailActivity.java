//package me.xiaocao.news.ui.wangyi;
//
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.text.Html;
//import android.view.Menu;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import butterknife.Bind;
//import me.xiaocao.news.R;
//import me.xiaocao.news.app.Constants;
//import me.xiaocao.news.helper.PresenterFactory;
//import me.xiaocao.news.model.ZhihuDetail;
//import me.xiaocao.news.model.event.NewsDetailEvent;
//import me.xiaocao.news.model.request.WangYiDetailRequest;
//import me.xiaocao.news.presenter.INewsPresenter;
//import x.lib.ui.BaseActivity;
//import x.lib.ui.TitleView;
//import x.lib.utils.GlideUtils;
//import x.lib.utils.ToastUtils;
//
//
///**
// * description: NewsDetailActivity
// * author: xiaocao
// * date: 17/7/5 下午6:00
// */
//public class WangyiDetailActivity extends BaseActivity {
//    @Override
//    protected int setContentViewResId() {
//        return 0;
//    }
//
//    @Override
//    protected void initTitle() {
//
//    }
//
//    @Override
//    protected void initInstance() {
//
//    }
////
////
////    @Bind(R.id.ivNewsImg)
////    ImageView ivNewsImg;
////    @Bind(R.id.tvNewsContent)
////    TextView tvNewsContent;
////    @Bind(R.id.tvNewsTitle)
////    TextView tvNewsTitle;
////    private INewsPresenter mPresenter;
////
////    @Override
////    public void onCreate(@Nullable Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////    }
////
////    @Override
////    protected int setContentViewResId() {
////        return R.layout.activity_news_detail;
////    }
////
////    @Override
////    protected void initTitle() {
////        TitleView title = new TitleView(activity, findViewById(R.id.toolbar));
////        title.setBack(activity);
////        title.setTitleText(getIntent().getExtras().getString(Constants.NEWS_Title));
////    }
////
////    @Override
////    protected boolean isRegisterEventBus() {
////        return true;
////    }
////
////    @Override
////    protected boolean isSupportSwipeBack() {
////        return true;
////    }
////
////    @Override
////    protected void initInstance() {
////        tvNewsTitle.setText(getIntent().getExtras().getString(Constants.NEWS_Title));
////        GlideUtils.loadImageView(activity, getIntent().getExtras().getString(Constants.NEWS_Img), ivNewsImg);
////        mPresenter = PresenterFactory.getNewsPresenter();
////        mPresenter.getNewsDetail(new WangYiDetailRequest().setId(getIntent().getExtras().getString(Constants.NEWS_ID)));
////
////    }
////
////    @Subscribe(threadMode = ThreadMode.MAIN)
////    public void onEvent(NewsDetailEvent event) {
////        switch (event.code) {
////            case NewsDetailEvent.code_success:
////                tvNewsContent.setText(Html.fromHtml(((ZhihuDetail) event.data).getBody()));
////                break;
////            case NewsDetailEvent.code_err:
////                ToastUtils.showShort(activity, (String) event.data);
////                break;
////        }
////    }
////
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
//////        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
//////        CollectionVo dbVo=CollectionDao.queryImgUrl(getIntent().getExtras().getString(Constants.NEWS_Img));
//////        if (null !=dbVo && dbVo.getImgUrl().equals(getIntent().getExtras().getString(Constants.NEWS_Img))){
//////            menu.getItem(0).setTitle("取消收藏");
//////        }else {
//////            menu.getItem(0).setTitle("添加收藏");
//////        }
////        return true;
////    }
////
//////    @Override
//////    public boolean onOptionsItemSelected(MenuItem item) {
//////        switch (item.getItemId()){
//////            case R.id.menuCollection:
//////                CollectionVo dbVo=CollectionDao.queryImgUrl(getIntent().getExtras().getString(Constants.NEWS_Img));
//////                if (null !=dbVo && dbVo.getImgUrl().equals(getIntent().getExtras().getString(Constants.NEWS_Img))){
//////                    CollectionDao.deleteChannel(dbVo.getId());
//////                    ToastUtils.showShort(activity,"已取消收藏");
//////                }else {
//////                    CollectionVo vo = new CollectionVo();
//////                    vo.setType(CollectionVo.TYPE_News);
//////                    vo.setImgUrl(getIntent().getExtras().getString(Constants.NEWS_Img));
//////                    vo.setTitle(getIntent().getExtras().getString(Constants.NEWS_Title));
//////                    vo.setNewsId(getIntent().getExtras().getString(Constants.NEWS_ID));
//////                    vo.setId(CollectionDao.queryAll().size()+1);
//////                    CollectionDao.insert(vo);
//////                    ToastUtils.showShort(activity,"已添加收藏");
//////                }
//////                supportInvalidateOptionsMenu();
//////                break;
//////            case R.id.menuShare:
//////                IntentUtil.shareText(activity, getIntent().getExtras().getString(Constants.NEWS_Title), getIntent().getExtras().getString(Constants.NEWS_HTML));
//////                break;
//////        }
//////        return true;
//////    }
////
////    @Override
////    protected void onDestroy() {
////        super.onDestroy();
////    }
//}
