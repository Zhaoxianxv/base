package com.yfy.app.netHttp;

import android.text.TextUtils;


import com.yfy.final_tag.stringtool.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 */
public class OkHttpClientParam {

    private static OkHttpClient client;

    private OkHttpClientParam() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static void create() {
        //缓存
//        int maxCacheSize = 10 * 1024 * 1024;
//        Cache cache = new Cache(App.getApp().getCacheDir(), maxCacheSize);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Logger.e(message);
            }
        });
        // set your desired log level:BASIC,HEADERS,BODY.
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Response response = chain.proceed(request);
                String cacheControl = request.cacheControl().toString();
                if (TextUtils.isEmpty(cacheControl)) {
                    cacheControl = "public, max-age=60 ,max-stale=2419200";
                }
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
        };
        client = new OkHttpClient.Builder()
                .addNetworkInterceptor(cacheInterceptor)
                .readTimeout(15, TimeUnit.SECONDS)//15s 读取超时
                .connectTimeout(15, TimeUnit.SECONDS)//15s 连接超时
                .addInterceptor(loggingInterceptor)
//                .cache(cache)
                .build();
    }

    public static OkHttpClient getClient() {
        if (client == null) {
            create();
        }
        return client;
    }
}
