package xyz.yhsj.videoparse.common;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.yhsj.videoparse.common.cookie.CookieJarImpl;
import xyz.yhsj.videoparse.common.cookie.store.MemoryCookieStore;

/**
 * A helper of retrofit2 for creating restful service easier.
 * <p/>
 * Created by LOVE on 2016/3/24.
 */
public class RetrofitClient {

    private static String HOST_NAME = "http://www.baidu.com";

    private static final RetrofitClient instance = new RetrofitClient();

    public static RetrofitClient getInstance() {
        return instance;
    }

    private Retrofit retrofit;

    public RetrofitClient() {
        createRetrofit();
    }

    private void createRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(HOST_NAME)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    public <T> T create(Class<?> clazz) {
        return (T) retrofit.create(clazz);
    }

}
