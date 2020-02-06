package com.gestaltsystech.karyawan_api.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIConfig {

    //public static final String BASE_URL = "http://training.bsbdistribusi.com/connection_android/test/";
    public static final String BASE_URL = "http://192.168.123.15/Adriansyah/karyawan/";
    //ip untuk localhost sesuaikan dengan ip komputer


    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setLenient();
            Gson gson = gsonBuilder.create();

            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}