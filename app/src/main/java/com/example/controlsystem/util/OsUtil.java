package com.example.controlsystem.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;


public class OsUtil {

    private static int screenwidth;
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }


    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static void setScreenWidth(int width) {
        screenwidth = width;
    }
    public static int getScreenWidth() {
        return screenwidth;
    }
    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeigth(Activity activity) {
        WindowManager windowManager =
                (WindowManager) activity.getApplication().getSystemService(Context.
                        WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // 可能有虚拟按键的情况
            display.getRealSize(outPoint);
        } else {
            // 不可能有虚拟按键
            display.getSize(outPoint);
        }
        int mRealSizeWidth;//手机屏幕真实宽度
        int mRealSizeHeight;//手机屏幕真实高度
        mRealSizeHeight = outPoint.y;
        mRealSizeWidth = outPoint.x;

        return mRealSizeHeight;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
//    public static int getScreenWidth(Activity activity) {
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
//        return outMetrics.widthPixels;
//
//    }
}
