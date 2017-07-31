package com.xiaocao.weiyue.ui.picture;



import com.xiaocao.weiyue.Constants;
import com.xiaocao.weiyue.R;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;
import x.lib.ui.BaseActivity;
import x.lib.ui.TitleView;
import x.lib.utils.GlideUtils;

public class PhotoActivity extends BaseActivity {
    @Bind(R.id.ivPhoto)
    PhotoView ivPhoto;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initTitle() {
        TitleView title = new TitleView(activity, findViewById(R.id.toolbar));
        title.setBack(activity);
        title.setTitleText("图片详情");
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    protected void initInstance() {
        GlideUtils.loadImageView(activity, getIntent().getExtras().getString(Constants.PHOTO_URL), ivPhoto);
    }

    @OnClick(R.id.ivPhoto)
    public void onClick() {
        finish();
    }
}
