package com.ygs.netronic.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ygs.netronic.annotations.RandomUserAnnotation;
import com.ygs.netronic.api.RandomUserApi;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RandomUserService {
    private final Retrofit mRetrofit;
    private static RandomUserService mService;


    private RandomUserService() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Accept-Encoding", "*")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .readTimeout(RandomUserAnnotation.READ_TIMEOUT,
                        TimeUnit.SECONDS)
                .connectTimeout(RandomUserAnnotation.
                        CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(RandomUserAnnotation.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @NonNull
    public static RandomUserService getInstance() {
        if (mService == null) {
            mService = new RandomUserService();
        }
        return mService;
    }

    public RandomUserApi getApi(){
        return mRetrofit.create(RandomUserApi.class);
    }


}
