package com.hangzhou.zhb.myelves.module.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hangzhou.zhb.myelves.http.MainHttpMethods;

public abstract class BaseFragment extends Fragment {
    public Context mContext;// 上下文
    public View mView;
    public MaterialDialog dialog;
    private MainHttpMethods mainHttpMethods = new MainHttpMethods();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = initView(inflater, savedInstanceState);
        return mView;
    }

    public MainHttpMethods getMainHttpMethods() {
        return mainHttpMethods;
    }

    /**
     * 初始化界面
     * */

    public abstract View initView(LayoutInflater inflater, Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    public abstract void initData(Bundle savedInstanceState);

    /**
     * 隐藏输入框的输入键盘
     */
    public void hideInput(View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
    }

}