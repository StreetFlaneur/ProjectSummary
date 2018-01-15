package com.sam.projectsummary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.dalong.marqueeview.MarqueeView;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;

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

    private String realStr = "";
    private MobileTextWatcher mobileTextWatcher;

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
