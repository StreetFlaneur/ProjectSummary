package com.sam.projectsummary;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
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
