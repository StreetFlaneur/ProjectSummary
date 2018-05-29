package com.techidea.sgsb.douban;

import com.library.data.RxBaseCase;
import com.library.data.entity.Book;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by sam on 2018/5/28.
 */

public class SearchBooksApi extends RxBaseCase<List<Book>>{

    @Override
    public RxBaseCase initParams(String... paras) {
        return null;
    }

    @Override
    public Observable<List<Book>> execute() {
        return null;
    }
}
