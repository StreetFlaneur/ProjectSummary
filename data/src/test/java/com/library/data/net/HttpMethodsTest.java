package com.library.data.net;

import com.library.data.entity.BookResult;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import rx.Observer;

/**
 * Created by sam on 2018/1/31.
 */

public class HttpMethodsTest {

    private Gson gson = new Gson();

    @Before
    public void setup() {
        RxTestHelper.registJavaImmediate();
        RxTestHelper.registAndroidImmediate();
    }

    @After
    public void tearDown() {
        RxTestHelper.rxReset();
    }

    @Test
    public void searchBook() {
        String keyword = "电商";
        String tag = "";
        String start = "1";
        String count = "10";
        HttpMethods.getInstance()
                .searchBook(keyword, tag, start, count).subscribe(new Observer<BookResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.print(throwable.getMessage());
            }

            @Override
            public void onNext(BookResult bookResult) {
                System.out.println(gson.toJson(bookResult));

            }
        });
    }

    @Test
    public void testOkHttpGet() throws IOException {
        OkHttpManager okHttpManager = OkHttpManager.getInstance();
        okHttpManager.setTest(true);
        String url = "https://api.douban.com/v2/book/search?";
      /*  @Query("q") String q,
        @Query("tag") String tag,
        @Query("start") String start,
        @Query("count") String count*/
        String keyword = "电商";
        String tag = "";
        String start = "1";
        String count = "10";
        String params = "q=" + keyword +
                "&tag=" + tag +
                "&start=" + start +
                "&count=" + count;
        okHttpManager.getDataSync(url + params, new OnHttpResultListener() {
            @Override
            public void onError(int code, String message) {
                System.out.print(message);
            }

            @Override
            public void onSuccess(String response) {
                System.out.print(response);
            }
        });
    }
}
