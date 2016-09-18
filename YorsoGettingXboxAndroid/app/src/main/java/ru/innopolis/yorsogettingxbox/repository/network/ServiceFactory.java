package ru.innopolis.yorsogettingxbox.repository.network;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {

    private static OkHttpClient sClient;

    private static volatile DocumentsApi sDocumentsService;
    private static volatile DealsApi sDealsService;

    public static final String API_ENDPOINT = "http://we-need-xbox.azurewebsites.net/api/";

    private ServiceFactory() {
    }

    @NonNull
    public static DocumentsApi getDocumentsApiService() {
        DocumentsApi service = sDocumentsService;
        if (service == null) {
            synchronized (ServiceFactory.class) {
                service = sDocumentsService;
                if (service == null) {
                    service = sDocumentsService = buildRetrofit().create(DocumentsApi.class);
                }
            }
        }
        return service;
    }

    @NonNull
    public static DealsApi getDealsApiService() {
        DealsApi service = sDealsService;
        if (service == null) {
            synchronized (ServiceFactory.class) {
                service = sDealsService;
                if (service == null) {
                    service = sDealsService = buildRetrofit().create(DealsApi.class);
                }
            }
        }
        return service;
    }

    public static void recreate() {
        sClient = null;
        sClient = getClient();
        sDocumentsService = buildRetrofit().create(DocumentsApi.class);
        sDealsService = buildRetrofit().create(DealsApi.class);
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @NonNull
    private static Gson getGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ServiceFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor.create())
                .build();
    }
}
