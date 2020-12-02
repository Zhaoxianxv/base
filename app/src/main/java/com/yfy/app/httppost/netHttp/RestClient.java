package com.yfy.app.httppost.netHttp;


import com.yfy.app.httppost.netHttp.service.AccountService;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created b
 */
public enum RestClient {
    instance;
    private AccountService accountService;

    RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerApiUrl.SERVER_ROOT)
                .client(CustomerOkHttpClient.getClient())
                .addConverterFactory(ScalarsConverterFactory.create()) //设置 Json 转换器
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        /*
         * 实例化Service们
         */
        accountService = retrofit.create(AccountService.class);
    }

    public AccountService getAccountService() {
        return accountService;
    }

}
