package com.techidea;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mob.MobApplication;
import com.mob.MobSDK;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.sharesdk.framework.ShareSDK;

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
        MobSDK.init(this, "", "");
//        CacheLoaderConfiguration cacheLoaderConfiguration  = new CacheLoaderConfiguration();
//        CacheLoaderManager.getInstance().init(this,
//        new HashCodeFileNameGenerator(), 1024 * 1024 * 8, 50, 20);
    }

    String weChatAppId = "";

    public void registWeChat() {
        wxApi = WXAPIFactory.createWXAPI(this, weChatAppId, false);
        wxApi.registerApp(weChatAppId);
    }

    public static IWXAPI getWeChatApi() {
        return wxApi;
    }
}
