package com.sam.projectsummary;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.robin.lazy.cache.CacheLoaderConfiguration;
import com.robin.lazy.cache.CacheLoaderManager;
import com.robin.lazy.cache.disk.naming.FileNameGenerator;
import com.robin.lazy.cache.disk.naming.HashCodeFileNameGenerator;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by sam on 2017/11/29.
 */

public class SummaryApplication extends Application {

    private static IWXAPI wxApi;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        registWeChat();
        CacheLoaderConfiguration cacheLoaderConfiguration  = new CacheLoaderConfiguration();
        CacheLoaderManager.getInstance().init(this,
                new HashCodeFileNameGenerator(), 1024 * 1024 * 8, 50, 20);
    }

    String weChatAppId = "wxd930ea5d5a258f4f";

    public void registWeChat() {
        wxApi = WXAPIFactory.createWXAPI(this, weChatAppId, false);
        wxApi.registerApp(weChatAppId);
    }

    public static IWXAPI getWeChatApi() {
        return wxApi;
    }
}
