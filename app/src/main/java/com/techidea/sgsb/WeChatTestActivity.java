package com.techidea.sgsb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.techidea.sgsb.common.Util;
import com.techidea.sgsb.widget.ClickableSpanEvent;
import com.techidea.sgsb.widget.NoUnlineSpan;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sam on 2017/11/29.
 */

public class WeChatTestActivity extends AppCompatActivity {

    private IWXAPI iwxapi;
    @BindView(R.id.edittext_filter)
    EditText editTextFilter;
    @BindView(R.id.imageviewTest)
    ImageView imageViewTest;
    @BindView(R.id.textview_test)
    TextView textViewTest;
    @BindView(R.id.text_animator)
    TextView textViewAnimator;


    private String realStr = "";
    private MobileTextWatcher mobileTextWatcher;
    private static final int THUMB_SIZE = 150;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat);
        ButterKnife.bind(this);

        iwxapi = SummaryApplication.getWeChatApi();
        mobileTextWatcher = new MobileTextWatcher();
       /* InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (dest.toString().length() > 4) {
                    return source.toString().replace(source.toString(), "*");
                } else {
                    return source;
                }
            }
        };
        editTextFilter.setFilters(new InputFilter[]{inputFilter});*/
        editTextFilter.addTextChangedListener(mobileTextWatcher);


        String url = "http://metro.3tichina.com:80/metro/metro/ShowImg/31321";
        Picasso.with(this)
                .load(url)
                .into(imageViewTest);

        String servicePre = "拨打电话拨打电话拨打电话拨打电话拨打电话";
        String serviceCall = "客户电话";
        String serviceCall5 = " 5555555";
        int oneCount = servicePre.length() + serviceCall.length();
        int count = servicePre.length() + serviceCall.length() + serviceCall5.length();

        String content = servicePre + serviceCall;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,
                R.color.red)), servicePre.length(), oneCount,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ClickableSpanEvent(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "kehudainnua", Toast.LENGTH_SHORT).show();
                    }
                }), servicePre.length(), oneCount,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        NoUnlineSpan noUnlineSpan = new NoUnlineSpan();
        spannableStringBuilder.setSpan(noUnlineSpan, servicePre.length(), oneCount,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        SpannableStringBuilder call5 = new SpannableStringBuilder(serviceCall5);
        call5.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,
                R.color.red)), 0, serviceCall5.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        call5.setSpan(new ClickableSpanEvent(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "555", Toast.LENGTH_SHORT).show();
                    }
                }), 0, serviceCall5.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        call5.setSpan(noUnlineSpan, 0, serviceCall5.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableStringBuilder.append(call5);

        textViewTest.setMovementMethod(LinkMovementMethod.getInstance());
        textViewTest.setText(spannableStringBuilder);
        textViewTest.setHighlightColor(getResources().getColor(android.R.color.transparent));
    }

    /**
     * 获取用于显示的带星花的字符，不提交后台
     * 1523****434
     */
    private String getDisplayStr(String realStr) {
        String result = new String(realStr);
        char[] cs = result.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (i >= 4 && i <= 7) {//把3和10区间的字符隐藏
                cs[i] = '*';
            }
        }
        return new String(cs);
    }

    class MobileTextWatcher implements TextWatcher {
        private String displayStr = "";//显示在editext上的字符串

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().trim().equals(displayStr.trim())) {//这个判断一定要写不然要陷入死循环（et.setText会触发onTextChanged我又在onTextChanged里使用了setText方法）
                return;
            }
            if (before > 0) {//删除
                String delStr = realStr.substring(start, start + before);
                String result = realStr.substring(0, start) + realStr.substring(start + before);
                realStr = result;
            }
            if (count > 0) {//输入
                if (realStr.length() >= 11) {
                    return;
                }
                CharSequence tmp = s.subSequence(start, start + count);
                StringBuilder sb = new StringBuilder(realStr);
                sb.insert(start, tmp);
                realStr = sb.toString();
                //realStr += tmp;
            }
            displayStr = getDisplayStr(realStr);
            editTextFilter.removeTextChangedListener(mobileTextWatcher);
            editTextFilter.setText(displayStr);
            editTextFilter.setSelection(displayStr.length());
            editTextFilter.addTextChangedListener(mobileTextWatcher);
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            /*System.out.println("---afterTextChanged---");
            */
        }
    }

    @OnClick(R.id.btn_authlogin)
    public void authLogin() {
        login();
    }

    @OnClick(R.id.btn_open_mp)
    public void openMiniProgram() {


        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = "gh_9dc0d68d0235"; // 填小程序原始id
//        req.path = path;                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 开发版，体验版和正式版
        iwxapi.sendReq(req);
    }

    @OnClick(R.id.btn_share_mp)
    public void shareMp() {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_9dc0d68d0235";     // 小程序原始id
//        miniProgramObj.path = "/pages/index/splash";            //小程序页面路径
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = "小程序消息Title";                    // 小程序消息title
        msg.description = "小程序消息Desc";               // 小程序消息desc
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.v2);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);                   // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        iwxapi.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void login() {
        // send oauth request
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        iwxapi.sendReq(req);
    }

    private void sendMessage() {

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "appdata";
        req.message = null;
        req.scene = 1;
        iwxapi.sendReq(req);
    }
}
