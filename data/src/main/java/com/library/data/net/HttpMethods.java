package com.library.data.net;

import com.library.data.HttpResult;
import com.library.data.RxUtils;
import com.library.data.entity.BookResult;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by sam on 2018/1/31.
 */

public class HttpMethods {

    private static final int DEFAULT_TIMEOUT_SECONDS = 60;
    private static final int DEFAULT_READ_TIMEOUT_SECONDS = 60;
    private static final int DEFAULT_WRITE_TIMEOUT_SECONDS = 60;

    private ApiService apiService;
    private Retrofit retrofit;
    private static final String BASE_URL = "https://api.douban.com/";

    private static volatile HttpMethods Instance;

    private HttpMethods() {
        init();
    }

    public static HttpMethods getInstance() {
        if (null == Instance) {
            synchronized (HttpMethods.class) {
                if (Instance == null) {
                    Instance = new HttpMethods();
                }
            }
        }
        return Instance;
    }

    public Observable<BookResult> searchBook(String keyword, String tag, String start, String count) {
        return apiService.searchBook(keyword, tag, start, count)
                .compose(RxUtils.<BookResult>rxSchedulerHelper())
                .onErrorResumeNext(new HttpExceptionFunc<BookResult>());
    }


    private void init() {
        OkHttpClient okHttpClient = getOkhttpClient();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        apiService = retrofit.create(ApiService.class);
    }



    private class HttpResultFunctionData<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            return httpResult.getData();
        }
    }

    private class HttpExceptionFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable throwable) {
            return Observable.error(throwable);
//            return Observable.error(ExceptionHandle.handleException(throwable));
        }
    }

    private OkHttpClient getOkhttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        //通一参数 增加共同的参数
//        basicParamsInterceptor =
//                new BasicParamsInterceptor.Builder()
//                        .addParam(KEY_TOKEN, TOKEN)
//                        .addParam(KEY_CONSUMER_ID, CONSUMER_ID)
//                        .build();
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .retryOnConnectionFailure(false)
                .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
    }


}
