package com.xiaocao.weiyue.ui.behavior;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;

import x.lib.utils.LogUtil;

import java.lang.reflect.Field;

/**
 * className: BottomNavigationViewHelper
 * author: lijun
 * date: 17/5/16 12:01
 */

public class BottomNavigationViewHelper {
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            LogUtil.err("BNVHelper", "Unable to get shift mode field" + e);
        } catch (IllegalAccessException e) {
            LogUtil.err("BNVHelper", "Unable to change value of shift mode" + e);
        }
    }
}
