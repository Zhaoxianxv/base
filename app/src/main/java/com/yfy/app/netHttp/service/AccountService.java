package com.yfy.app.netHttp.service;



import com.yfy.app.netHttp.ServerApiUrl;
import com.yfy.app.netHttp.result.BaseResResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
