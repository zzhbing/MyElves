package com.hangzhou.zhb.myelves.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import static android.R.attr.duration;

/**
 * 有多条Toast时只显示最后一条
 */
abstract class MyToast {
    private static Toast toast;
    private static Handler handler = new Handler();

    private static Runnable run = new Runnable() {
        public void run() {
            toast.cancel();
        }
    };

    private static void toast(Context ctx, CharSequence msg) {
        handler.removeCallbacks(run);

        if (null != toast) {
            toast.setText(msg);
        } else {
            toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
        }
        handler.postDelayed(run, duration);
        toast.show();
    }
 
    /**
     * 弹出Toast
     * 
     * @param ctx
     *            弹出Toast的上下文
     * @param msg
     *            弹出Toast的内容
     */
    static void show(Context ctx, CharSequence msg)
            throws NullPointerException {
        if (null == ctx) {
            throw new NullPointerException("The ctx is null!");
        }
        toast(ctx, msg);
    }
 
    /**
     * 弹出Toast
     * 
     * @param ctx
     *            弹出Toast的上下文
     * @param resId
     *            弹出Toast的内容的资源ID
     */
    static void show(Context ctx, int resId)
            throws NullPointerException {
        if (null == ctx) {
            throw new NullPointerException("The ctx is null!");
        }
        toast(ctx, ctx.getResources().getString(resId));
    }
 
}