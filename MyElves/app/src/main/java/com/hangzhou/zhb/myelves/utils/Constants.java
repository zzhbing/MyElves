package com.hangzhou.zhb.myelves.utils;

/**
 * Created by zhb on 2017/8/16.
 * 配置文件
 */

public class Constants {
    /**网络请求超时秒数*/
    public static final int DEFAULT_TIMEOUT = 10;
    /**根网络地址*/  //todo 测试用
    public static final String BASE_URL = "http://118.31.38.245:8080/HZYW/";
//    public static final String BASE_URL = "http://192.168.57.61:8080/HZYW/";

    /**SharedPreferences缓存文件名*/
    public static final String PARAMS = "FJJL_APP_PARAMS";

    //个推推送通知
    public static int NOTIFICATIONID = 0;

    //系统更新通知
    public static int NOTIFICATIONID_APP = 7000;

    private static final String ROOT_PATH = FileUtil.getSDPath() + "/us";
    public static final String APK_PATH = ROOT_PATH + "/apk";
}
