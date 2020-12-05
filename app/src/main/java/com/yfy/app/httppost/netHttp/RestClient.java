package com.yfy.app.httppost.netHttp;


import com.yfy.app.httppost.netHttp.service.AccountApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 详细用法请参考：http://square.github.io/retrofit/
 */
public enum RestClient {
    instance;
    private AccountApi accountService;

    RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.SERVER_ROOT)
                .client(OkHttpClientParam.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        /*
         * 实例化Service们
         */
        accountService = retrofit.create(AccountApi.class);
    }

    public AccountApi getAccountApi() {
        return accountService;
    }

}
