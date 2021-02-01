package com.yfy.app.netHttp;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 详细用法请参考：http://square.github.io/retrofit/
 * https://www.jianshu.com/p/2e8b400909b7
 */
public enum RestClient {
    instance;
    public AccountApi accountService;

    RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.SERVER_ROOT)
                .client(OkHttpClientParam.getClient())
                .addConverterFactory(GsonConverterFactory.create())//这个配置是将服务器返回的json字符串转化为对象
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//这个是用来决定你的返回值是Observable还是Call。
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
