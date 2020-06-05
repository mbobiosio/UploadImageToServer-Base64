package com.mbobiosio.uploadimagetoserver.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * UploadImageToServer-Base64
 * Created by Mbuodile Obiosio on Jun 05, 2020
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public class RetrofitServer {


    private static final String BASE_URL = "http://your-url.com/uploads/";
    private static Retrofit mRetrofit;
    public static final int CONNECT_TIMEOUT = 60;

    public static Retrofit getRetrofit() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .callTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return mRetrofit;
    }
}
