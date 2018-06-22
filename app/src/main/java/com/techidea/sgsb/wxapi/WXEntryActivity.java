package com.techidea.sgsb.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.techidea.sgsb.WeChatTestActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.sharesdk.wechat.utils.WXAppExtendObject;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sharesdk.wechat.utils.WechatHandlerActivity;

/**
 * Created by sam on 2018/5/28.
 */

public class WXEntryActivity extends WechatHandlerActivity{

    private IWXAPI iwxapi;

    /**
     * 处理微信发出的向第三方应用请求app message
     * <p>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
     * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
    public void onGetMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null) {
            Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
            startActivity(iLaunchMyself);
        }
    }

    /**
     * 处理微信向第三方应用发起的消息
     * <p>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
     * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
     * 回调。
     * <p>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */
    public void onShowMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null && msg.mediaObject != null
                && (msg.mediaObject instanceof WXAppExtendObject)) {
            WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
            Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        iwxapi = WXAPIFactory.createWXAPI(this, "", true);
//        iwxapi.handleIntent(this.getIntent(), this);
//    }
//
//
//
//    //小程序分享卡片,点击卡片回调
//    @Override
//    public void onReq(BaseReq req) {
//        Log.w("onReq", "打开小程序");
//        switch (req.getType()) {
//            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
////                goToGetMsg();
//                break;
//            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
////                goToShowMsg((ShowMessageFromWX.Req) req);
//                startActivity(new Intent(this, WeChatTestActivity.class));
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//    }
//
//    @Override
//    public void onResp(BaseResp resp) {
//        Log.w("onResp", "打开小程序");
//        if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
//            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) resp;
//            String extraData = launchMiniProResp.extMsg; // 对应JsApi navigateBackApplication中的extraData字段数据
//            Toast.makeText(this, "openid = " + resp.openId + " " + extraData, Toast.LENGTH_SHORT).show();
////            startActivity(new Intent(this, WeChatTestActivity.class));
//        }
//        startActivity(new Intent(this, WeChatTestActivity.class));


//        Toast.makeText(this, "openid = " + resp.openId, Toast.LENGTH_SHORT).show();
//        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
//            Toast.makeText(this, "code = " + ((SendAuth.Resp) resp).code, Toast.LENGTH_SHORT).show();
//        }
//
//        int result = 0;
//
//        switch (resp.errCode) {
//            case BaseResp.ErrCode.ERR_OK:
//
//                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
//
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//
//                break;
//            default:
//
//                break;
//        }
//
//        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//    }
}
