package com.techidea.sgsb.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.techidea.sgsb.SummaryApplication;
import com.techidea.sgsb.WeChatTestActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by sam on 2018/5/28.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI iwxapi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi = WXAPIFactory.createWXAPI(this, "", true);
        iwxapi.handleIntent(this.getIntent(), this);
    }

    //小程序分享卡片,点击卡片回调
    @Override
    public void onReq(BaseReq req) {
        Log.w("onReq", "打开小程序");
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//                goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//                goToShowMsg((ShowMessageFromWX.Req) req);
                startActivity(new Intent(this, WeChatTestActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.w("onResp", "打开小程序");
        if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) resp;
            String extraData = launchMiniProResp.extMsg; // 对应JsApi navigateBackApplication中的extraData字段数据
            Toast.makeText(this, "openid = " + resp.openId + " " + extraData, Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(this, WeChatTestActivity.class));
        }
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
    }
}
