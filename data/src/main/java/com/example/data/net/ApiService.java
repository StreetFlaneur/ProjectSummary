package com.example.data.net;

import com.example.data.HttpResult;
import com.example.data.entity.Book;
import com.example.data.entity.BookResult;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
}
