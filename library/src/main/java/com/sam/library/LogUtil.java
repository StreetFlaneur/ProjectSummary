package com.sam.library;

import android.util.Log;

/**
 * Created by zc on 2017/9/17.
 */

public class LogUtil {
    private static final boolean DEBUG = false;

    public static void i(String log) {
        if (!DEBUG) return;
        Log.i("RefreshLoadMoreLayout", log);
    }
}
