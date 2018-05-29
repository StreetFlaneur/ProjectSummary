package com.library.leftdrawer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient

import com.techidea.library.utils.WebViewUtils
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * Created by sam on 2018/2/6.
 */

class WebViewActivity : AppCompatActivity() {


    private val url = "http://api.metro.com.cn/lpwebapptest/?openid=oJ9_hjrbjh4HosrSH0c5_Dde1t-M"
    //    private String url = "https://map.baidu.com/";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        initView()
    }

    private fun initView() {
        webview.clearCache(true)
        webview.clearHistory()
        WebViewUtils.setJsEnable(webview.getSettings(), applicationContext)
        webview.setWebViewClient(object : WebViewClient() {
            //当点击链接时,希望覆盖而不是打开新窗口
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)  //加载新的url
                return true    //返回true,代表事件已处理,事件流到此终止
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
            }
        })

        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        webview.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
                    webview.goBack()   //后退
                    return@OnKeyListener true    //已处理
                }
            }
            false
        })
        webview.loadUrl(url)
    }
}
