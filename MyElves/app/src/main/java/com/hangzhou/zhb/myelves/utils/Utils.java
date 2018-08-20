package com.hangzhou.zhb.myelves.utils;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by zhb on 2017/8/16.
 *
 */

public class Utils {
    /**
     * @param context 上下文
     * @param info    字符串展示内容
     */
    public static void show(Context context, String info) {
        MyToast.show(context, info);
    }

    /**
     * @param context 上下文
     * @param info    字符串id值
     */
    public static void show(Context context, int info) {
        MyToast.show(context, info);
    }

    /**
     * 判断网络是否可用
     * @return true 可用；false 不可用
     */
    public static boolean ping() {
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " + ip);// ping网址1次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }
}
