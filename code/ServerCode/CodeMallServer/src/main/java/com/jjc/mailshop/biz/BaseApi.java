package com.jjc.mailshop.biz;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 基类  API
 */
public class BaseApi {
    private Retrofit retrofit = null;
    private OkHttpClient client = null;
    public static final String AUTHORIZATION = "Authorization";
    private static final long connectTimeoutMills = 10 * 1000L;
    private static final long readTimeoutMills = 10 * 1000L;

    private static BaseApi instance;

    private BaseApi() {

    }

    public static synchronized BaseApi getInstance() {
        if (instance == null)
            instance = new BaseApi();
        return instance;
    }
    /***
     * 外部传递url
     *
     * @param service 服务
     * @param baseUrl url
     * @param <S> 泛型
     * @return 泛型
     */
    public static <S> S get(Class<S> service, String baseUrl) {
        return getInstance().getRetrofit( baseUrl).create(service);
    }


    /**
     * 传递url
     *
     * @param baseUrl
     * @return
     */
    public Retrofit getRetrofit(String baseUrl) {
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create());
            builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            retrofit = builder.build();
        }
        return retrofit;
    }

    public OkHttpClient getClient() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(connectTimeoutMills, TimeUnit.MILLISECONDS);
            builder.readTimeout(readTimeoutMills, TimeUnit.MILLISECONDS);

            client = builder.build();
        }
        return client;
    }

}
