package com.techidea.sgsb.douban;

/**
 * Created by sam on 2018/5/28.
 */

public interface DoubanBooksContact {

    interface Presenter {
        void searchBooks(String keywords, String tag, String start, String count);
    }

    interface View {

    }
}
