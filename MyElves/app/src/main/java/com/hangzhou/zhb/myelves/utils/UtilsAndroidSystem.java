package com.hangzhou.zhb.myelves.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.Locale;

/**
 * Created by zhb on 2018/5/4
 * 作用：获取手机系统的一些信息
 */
public class UtilsAndroidSystem {
    private Context mContext;

    public UtilsAndroidSystem(Context mContext){
        this.mContext = mContext;
    }

    /**
     * 获取屏幕宽度
     * @return 屏幕宽度,单位px
     */
    private int getDeviceWidth(){
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return 屏幕高度,单位px
     */
    private int getDeviceHeight(){
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取设备唯一标志
     * 需要Manifest.permission.READ_PHONE_STATE权限
     * @return IMEI
     */
//    private String getIMEI(){
//        String deviceID = "";
//        try{
//            TelephonyManager mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
//            if(mTelephonyManager != null){
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    deviceID = mTelephonyManager.getImei();
//                }else {
//                    deviceID = mTelephonyManager.getDeviceId();
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return deviceID;
//    }

    /**
     * @return 获取手机厂商名
     */
    private String getDeviceManufacturer(){
        return Build.MANUFACTURER;
    }

    /**
     * @return 获取手机产品名
     */
    private String getDeviceProduct(){
        return Build.PRODUCT;
    }

    /**
     * @return 获取手机型号
     */
    private String getDeviceModel(){
        return Build.MODEL;
    }

    /**
     * @return 获取手机主板名
     */
    private String getDeviceBoard(){
        return Build.BOARD;
    }

    /**
     * @return 获取手机设备名
     */
    private String getDeviceDevice(){
        return Build.DEVICE;
    }

    /**
     * @return 获取手机硬件序列号
     */
    @SuppressLint("HardwareIds")
    private String getDeviceSerial(){
        return Build.SERIAL;
    }

    /**
     * @return 获取手机Android系统SDK
     */
    private int getDeviceSDK(){
        return Build.VERSION.SDK_INT;
    }

    /**
     * @return 获取手机Android版本
     */
    private String getDeviceAndroidVersion(){
        return Build.VERSION.RELEASE;
    }

    /**
     * @return 获取手机当前系统语言
     */
    private String getDeviceDefaultLanguage(){
        return Locale.getDefault().getLanguage();
    }

    /**
     * @return 获取手机当前系统上的语言列表（Locale列表）
     */
    private Locale[] getDeviceSupportLanguage(){
        return Locale.getAvailableLocales();
    }



}
