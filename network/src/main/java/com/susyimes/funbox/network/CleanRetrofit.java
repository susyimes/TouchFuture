package com.susyimes.funbox.network;

/**
 * Created by Susyimes on 2018/1/23.
 */

import android.content.Context;
import android.content.pm.PackageManager;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

@SuppressWarnings("WeakerAccess")
public class CleanRetrofit {

    final HuobiAPI service;


    public static boolean debug = true;
    private final static int CACHE_SIZE_BYTES = 1024 * 1024 * 2;


    public static String ENDPOINT = "https://api.huobi.pro";


    private Context mContext;

    CleanRetrofit(Context context) {
        mContext = context.getApplicationContext();

        String pkName = mContext.getPackageName();

        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                //.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                .create();
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .client(okHttpClient(context));



        Retrofit retrofit = builder.build();


        service = retrofit.create(HuobiAPI.class);

    }

    private OkHttpClient okHttpClient(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder().cache(new Cache(context.getCacheDir(), CACHE_SIZE_BYTES))
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request oldRequest = chain.request();
                        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                                .newBuilder()
                                .scheme(oldRequest.url().scheme())
                                .host(oldRequest.url().host());

                        Request newRequest = oldRequest.newBuilder()
                                .method(oldRequest.method(), oldRequest.body())
                                .header("Content-Type","application/json")
                                .header("User-Agent", "Chrome/39.0.2171.71" )
                                .url(authorizedUrlBuilder.build())
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(logging)
                //.addInterceptor(new AddHeaderInterceptor())
                //.addInterceptor(new ReceivedCookiesInterceptor(mContext))
                //.addInterceptor(new AddCookiesInterceptor(mContext))
                //.cache(providesCache())
                .build();
    }

//    Cache providesCache() {
//        File httpCacheFile = FileUtils.getDiskCacheDir("response");
//        return new Cache(httpCacheFile, 1024 * 100 * 1024);
//    }


    HuobiAPI getService() {
        return service;
    }


}
