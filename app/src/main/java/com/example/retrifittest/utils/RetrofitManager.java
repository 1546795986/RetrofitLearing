package com.example.retrifittest.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.2.2.2:9102")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static Retrofit getRetrofit()
    {
        return retrofit;
    }
}
