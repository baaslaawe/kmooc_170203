package com.nile.kmooc.core;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Provider;

import com.nile.kmooc.util.Config;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider implements Provider<Retrofit> {

    @Inject
    Config config;

    @Inject
    OkHttpClient client;

    @Inject
    Gson gson;

    @Override
    public Retrofit get() {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(config.getApiHostURL())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
