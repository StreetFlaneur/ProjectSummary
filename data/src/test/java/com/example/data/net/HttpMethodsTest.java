package com.example.data.net;

import com.example.data.entity.Book;
import com.example.data.entity.BookResult;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
}
