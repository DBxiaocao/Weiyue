package x.lib.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
/** 
 * description: DialogUtil
 * author: lijun
 * date: 17/8/24 下午5:01
*/
public class DialogUtil {

    public static AlertDialog.Builder showDialog(final Context context,
                                                 String message) {
        // SysApplication.getInstance().exit();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        return builder;
    }

    public static AlertDialog.Builder showDialog(Context context, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        return builder;
    }

    public static AlertDialog.Builder showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

}
