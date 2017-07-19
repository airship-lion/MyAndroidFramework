package study.toonan.com.myframework.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ProjectName EBase
 * Author Eugene
 * Email 286983858@qq.com
 * CreateTime 16/4/21 19:14
 */
public class RetrofitManager {
    private static final int READ_TIMEOUT = 90;
    private static final int CONNECT_TIMEOUT = 5;
    private Retrofit retrofit;
    public RetrofitService retrofitService;

    public OkHttpClient getClient() {
        return client;
    }

    protected OkHttpClient client;

    private static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitManager() {
        createService();
    }

    private void createService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
        String baseUrl = "http://112.74.17.193:8080/platform/app/";
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    public void rebuildService() {
        createService();
    }

}
