package com.hangzhou.zhb.myelves.http;

import com.hangzhou.zhb.myelves.response.login.LoginResponse;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by zhb on 2017/8/16.
 *
 */

public interface MainApiService {
    // 登录
    @GET("service/login.htm")
    Observable<LoginResponse> login(
            @Query("name") String name,
            @Query("pwd") String pwd,
            @Query("deviceCode") String deviceCode,
            @Query("deviceType") String deviceType); // 0:IOS;1:Android
}
