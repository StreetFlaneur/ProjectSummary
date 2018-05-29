package com.techidea.sgsb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.techidea.library.utils.WebViewUtils;
import com.techidea.library.widget.ProgressBarWebView;
import com.techidea.sgsb.constant.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sam on 2018/1/15.
 */

public class WebViewLocation extends AppCompatActivity {

    @BindView(R.id.progress_webview)
    ProgressBarWebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_location);
        ButterKnife.bind(this);
        webView.loadUrl(Constants.TEST_URL);

        WebSettings webSettings = webView.getSettings();
        WebViewUtils.setLocationCache(webSettings, getApplicationContext());
        webView.setWebViewClient(new WebViewClient() {
            //当点击链接时,希望覆盖而不是打开新窗口
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);  //加载新的url
                return true;    //返回true,代表事件已处理,事件流到此终止
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });

    }
}
