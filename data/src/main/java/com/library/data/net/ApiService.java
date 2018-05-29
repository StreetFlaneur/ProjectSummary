package com.library.data.net;

import com.library.data.HttpResult;
import com.library.data.entity.BookResult;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sam on 2018/1/31.
 */

public interface ApiService {

    @GET("v2/book/search")
    Observable<BookResult> searchBook(
            @Query("q") String q,
            @Query("tag") String tag,
            @Query("start") String start,
            @Query("count") String count
    );

    @Multipart
    @POST("showCircle/photoUpload")
    Observable<HttpResult<String>> photoUpload(
            @Part MultipartBody.Part part
    );
}
