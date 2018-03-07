package x.lib.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.xiaocao.lib.R;

import x.lib.ui.BaseActivity;

/**
 * description: ThemeUtils
 * author: lijun
 * date: 17/8/28 17:30
 */

public class ThemeUtils {
    public static void changeThemeClose(Context context, Theme theme) {
        if (context == null)
            return;
        int style = R.style.SlideClose_Transparent_Red_Theme;
        switch (theme) {
            case BROWN:
                style = R.style.SlideClose_Transparent_Brown_Theme;
                break;
            case BLUE:
                style = R.style.SlideClose_Transparent_Blue_Theme;
                break;
            case BLUE_GREY:
                style = R.style.SlideClose_Transparent_BlueGrey_Theme;
                break;
            case YELLOW:
                style = R.style.SlideClose_Transparent_Yellow_Theme;
                break;
            case DEEP_PURPLE:
                style = R.style.SlideClose_Transparent_DeepPurple_Theme;
                break;
            case PINK:
                style = R.style.SlideClose_Transparent_Pink_Theme;
                break;
            case GREEN:
                style = R.style.SlideClose_Transparent_Green_Theme;
                break;
            default:
                break;
        }
        context.setTheme(style);
    }

    public static void changeTheme(Context context, Theme theme) {
        if (context == null)
            return;
        int style = R.style.RedTheme;
        switch (theme) {
            case BROWN:
                style = R.style.BrownTheme;
                break;
            case BLUE:
                style = R.style.BlueTheme;
                break;
            case BLUE_GREY:
                style = R.style.BlueGreyTheme;
                break;
            case YELLOW:
                style = R.style.YellowTheme;
                break;
            case DEEP_PURPLE:
                style = R.style.DeepPurpleTheme;
                break;
            case PINK:
                style = R.style.PinkTheme;
                break;
            case GREEN:
                style = R.style.GreenTheme;
                break;
            default:
                break;
        }
        context.setTheme(style);
    }

    public static void changeTextSize(Context context, SizeTheme theme) {
        if (context == null)
            return;
        int style = R.style.Theme_14;
        switch (theme) {
            case size14:
                style = R.style.Theme_14;
                break;
            case size15:
                style = R.style.Theme_15;
                break;
            case size16:
                style = R.style.Theme_16;
                break;
        }
        context.setTheme(style);
    }

    public static int changeTextSize(SizeTheme theme){
        int style = R.style.Theme_14;
        switch (theme) {
            case size14:
                style = R.style.Theme_14;
                break;
            case size15:
                style = R.style.Theme_15;
                break;
            case size16:
                style = R.style.Theme_16;
                break;
        }
        return style;
    }

    public static SizeTheme getSizeCurrentTheme(){
        int value=SPUtils.getInstance().getInt(BaseActivity.isTextSize,0);
        return ThemeUtils.SizeTheme.mapValueToTheme(value);
    }

    public static Theme getCurrentTheme() {
        int value = SPUtils.getInstance().getInt(BaseActivity.isTheme, 0);
        return ThemeUtils.Theme.mapValueToTheme(value);
    }

    public enum Theme {
        RED(0x00),
        BROWN(0x01),
        BLUE(0x02),
        BLUE_GREY(0x03),
        YELLOW(0x04),
        DEEP_PURPLE(0x05),
        PINK(0x06),
        GREEN(0x07);

        private int mValue;

        Theme(int value) {
            this.mValue = value;
        }

        public static Theme mapValueToTheme(final int value) {
            for (Theme theme : Theme.values()) {
                if (value == theme.getIntValue()) {
                    return theme;
                }
            }
            // If run here, return default
            return RED;
        }

        static Theme getDefault() {
            return RED;
        }

        public int getIntValue() {
            return mValue;
        }
    }

    public enum SizeTheme {
        size14(0x00),
        size15(0x01),
        size16(0x02);

        private int mValue;

        SizeTheme(int value) {
            this.mValue = value;
        }

        public static SizeTheme mapValueToTheme(final int value) {
            for (SizeTheme theme : SizeTheme.values()) {
                if (value == theme.getIntValue()) {
                    return theme;
                }
            }
            // If run here, return default
            return size14;
        }

        static SizeTheme getDefault() {
            return size14;
        }

        public int getIntValue() {
            return mValue;
        }
    }
}
