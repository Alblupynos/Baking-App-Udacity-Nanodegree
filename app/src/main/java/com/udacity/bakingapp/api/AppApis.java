package com.udacity.bakingapp.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppApis {
    private static final Object LOCK = new Object();
    private static AppApis sInstance;

    private Retrofit retrofit;

    public AppApis(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public static AppApis getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                sInstance = new AppApis(retrofit);
            }
        }
        return sInstance;
    }

    public BakingApi bakingApi() {
        return retrofit.create(BakingApi.class);
    }
}
