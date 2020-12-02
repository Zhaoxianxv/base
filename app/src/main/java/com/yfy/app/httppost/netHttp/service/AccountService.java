package com.yfy.app.httppost.netHttp.service;



import com.yfy.app.httppost.netHttp.ServerApiUrl;
import com.yfy.app.httppost.netHttp.result.BaseResResult;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created
 * 封装与Account相关的请求
 */
public interface AccountService {
    /**
     */
    @FormUrlEncoded
    @POST(ServerApiUrl.Get_Code)
    Observable<BaseResResult> updateFile();



}
