package com.hangzhou.zhb.myelves.http;

import com.hangzhou.zhb.myelves.utils.Constants;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by zhb on 2017/8/16.
 *
 */

public class MainHttpMethods {
    private MainApiService mainApiService;
    public MainHttpMethods() {
        OkHttpClient client = new OkHttpClient();
        // 设置网络请求超时时间
        client.setReadTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        client.setWriteTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        client.setConnectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASE_URL)//real
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mainApiService = retrofit.create(MainApiService.class);
    }

    public MainApiService getApiService() {
        return mainApiService;
    }
}
