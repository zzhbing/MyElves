package com.hangzhou.zhb.myelves.utils;

import android.content.Context;

/**
 * Created by zhb on 2018/5/5
 * 作用：字符串处理
 */
public class MyString {

    public MyString(){

    }

    /**
     * @param mString 字符串
     * @return null或“”时为true，否则为false
     */
    public boolean isNON(String mString){
        return mString.isEmpty();
    }

    /**
     * 字符串拼接
     * @param s1 字符串1
     * @param s2 字符串2
     * @return s1 + s2
     */
    public String add(String s1 , String s2){
        StringBuilder sb = new StringBuilder(s1);
        return String.valueOf(sb.append(s2));
    }

    /**
     * 以{#}分隔
     * @param bb
     * @return
     */
    public String[] cut(String bb){
        return bb.split("\\{#\\}");
    }
}
