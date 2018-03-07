package me.xiaocao.news.ui.pic;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.OnClick;
import me.xiaocao.news.R;
import me.xiaocao.news.app.Constants;
import uk.co.senab.photoview.PhotoView;
import x.lib.ui.BaseActivity;
import x.lib.ui.TitleView;
import x.lib.utils.FileUtils;
import x.lib.utils.GlideUtils;
import x.lib.utils.SPUtils;
import x.lib.utils.SnackbarUtils;

public class PhotoActivity extends BaseActivity {
    @Bind(R.id.ivPhoto)
    PhotoView ivPhoto;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

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
        verifyStoragePermissions(activity);
        GlideUtils.loadImageView(activity, getIntent().getExtras().getString(Constants.PHOTO_URL), ivPhoto);
    }

    @OnClick(R.id.ivPhoto)
    public void onClick() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pic_dow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_dow) {
            final ProgressDialog dialog = ProgressDialog.show(this, "提示", "正在下载中...");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bitmap bitmap = Glide.with(activity)
                                .load(getIntent().getExtras().getString(Constants.PHOTO_URL))
                                .asBitmap()
                                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .get();
                        if (bitmap != null) {
                            String path=FileUtils.savePic(bitmap, Environment.getExternalStorageDirectory() + "/简洁新闻");
                            dialog.dismiss();
                            SnackbarUtils.with(ivPhoto).setMessage("已下载到" + path).setBgColor(getResources().getIntArray(R.array.colors)[SPUtils.getInstance().getInt(BaseActivity.isTheme, 0)])
                                    .setMessageColor(getResources().getColor(android.R.color.white)).show();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void verifyStoragePermissions(AppCompatActivity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
