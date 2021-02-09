package com.ehealth4everyone.restapi;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ehealth4everyone.restapi.models.FilterLists;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * eHealth4Everyone Test
 * <p>
 * Singleton Class.
 *
 * @author Gbayesola Emmanuel
 * @role Software Engineer
 * @created 31/1/2021 02:00 AM
 */

public class GbayesolaEmmanuelCloud {
    @SuppressLint("StaticFieldLeak")
    private static GbayesolaEmmanuelCloud instance;
    private static GbayesolaEmmanuelAPI apiService;

    Context context;


    /**
     * Private eHealth4Everyone Test constructor.
     *
     * @param context Application Context.
     */
    private GbayesolaEmmanuelCloud(final Context context) {
        this.context = context;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(context.getCacheDir(), 10 * 1024 * 1024))
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    if (hasNetwork(context))
                        request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                    else
                        request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                    return chain.proceed(request);
                })
                .build();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            // Intercept and Log Response Body in Debug Mode.
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
        }


        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

        retrofitBuilder.addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient);

        apiService = retrofitBuilder.baseUrl(GbayesolaEmmanuelCloudConfig.getInstance().getBaseUrl()).build().create(GbayesolaEmmanuelAPI.class);
    }

    public static Boolean hasNetwork(Context context) {
        boolean isConnected = false; // Initial Value
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting())
            isConnected = true;
        return isConnected;
    }

    /**
     * Singleton Get instance method
     *
     * @param context Application Context.
     * @return eHealth4Everyone Test  Instance.
     */
    public static GbayesolaEmmanuelCloud getInstance(Context context) {
        if (instance == null) {
            synchronized (GbayesolaEmmanuelCloud.class) {
                if (instance == null) {
                    instance = new GbayesolaEmmanuelCloud(context);
                }
            }
        }
        return instance;
    }

    /**
     * Get all filter list.
     *
     * @param ID    App ID.
     * @param callback Retrofit Callback.
     */
    public void getFilterLists(String ID, Callback<List<FilterLists>> callback) {
        Call<List<FilterLists>> asyncFetch = apiService.getFilterLists(ID);
        asyncFetch.enqueue(callback);
    }


}