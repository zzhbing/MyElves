package com.hangzhou.zhb.myelves.utils;

import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import com.hangzhou.zhb.myelves.module.base.BaseFragment;

import java.lang.ref.WeakReference;

/**
 * Created by zhb on 2018/3/7.
 * handler共用类
 */

public class MyHandler extends Handler {

    private OnHandleMessageReturnListener onHandleMessageReturnListener;

    public interface OnHandleMessageReturnListener {
        void onHandleMessageReturn(Message msg);
    }

    public void setOnHandleMessageReturnListener(OnHandleMessageReturnListener onHandleMessageReturnListener) {
        this.onHandleMessageReturnListener = onHandleMessageReturnListener;
    }

    private final WeakReference<AppCompatActivity> mActivity;
    private final WeakReference<BaseFragment> mFragment;

    public MyHandler(AppCompatActivity activity) {
        mActivity = new WeakReference<>(activity);
        mFragment = null;
    }

    public MyHandler(BaseFragment baseFragment) {
        mActivity = null;
        mFragment = new WeakReference<>(baseFragment);
    }

    @Override
    public void handleMessage(Message msg) {
        if (onHandleMessageReturnListener != null) {
            AppCompatActivity activity = null;
            if (mActivity != null) {
                activity = mActivity.get();
            }
            BaseFragment fragment = null;
            if (mFragment != null) {
                fragment = mFragment.get();
            }
            if (activity != null || fragment != null) {
                onHandleMessageReturnListener.onHandleMessageReturn(msg);
            }
        }
    }
}
