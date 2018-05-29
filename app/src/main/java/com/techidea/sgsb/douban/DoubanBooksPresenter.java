package com.techidea.sgsb.douban;

/**
 * Created by sam on 2018/5/28.
 */

public class DoubanBooksPresenter implements DoubanBooksContact.Presenter {

    private DoubanBooksContact.View view;

    public DoubanBooksPresenter(DoubanBooksContact.View view) {
        this.view = view;
    }

    @Override
    public void searchBooks(String keywords, String tag, String start, String count) {

    }
}
