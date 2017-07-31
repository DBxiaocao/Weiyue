package x.lib.ui;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.xiaocao.lib.R;

/**
 * description: BasePopupWindow
 * author: xiaocao
 * date: 17/7/24 20:40
 */

public abstract class BasePopupWindow extends PopupWindow {


    public BasePopupWindow(Context context) {
        super(context);//适配SDK2.3
        setBackgroundDrawable(new BitmapDrawable());//处理调用了super(context);后窗体四周出现1DP左右的padding（系统）
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(getMainView(context));
        this.setAnimationStyle(R.style.popwindow_anim_style);
    }

    @Override
    public void setAnimationStyle(int animationStyle) {
        super.setAnimationStyle(animationStyle);
    }

    abstract protected View getMainView(Context context);
}