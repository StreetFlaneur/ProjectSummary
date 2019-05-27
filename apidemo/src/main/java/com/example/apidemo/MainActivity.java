package com.example.apidemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static java.lang.System.in;

/**
 * Created by sam on 2018/5/28.
 */

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private String appendJs = "document.getElementById(\"btn-finish\").addEventListener(\"click\",function(){\n" +
            "\talert(\"test\")\n" +
            "})";

    private String url = "http://finnair.3tilabs.com/test2.html";

    String netjs = "var newscript = document.createElement(\"script\");"+
            "newscript.src=\"http://www.123.456/789.js\";" +
            "document.body.appendChild(newscript);";


    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String js = data.get("js").toString();
            webView.loadUrl("javascript:" + js);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webvidew);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new HackJs(), "hackObj");
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webView.loadUrl("javascript:"+netjs);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String tempJs = getHackJsFromNet();
//                        Message msg = new Message();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("js",tempJs);
//                        msg.setData(bundle);
//                        handler.sendMessage(msg);
//                    }
//                }).start();

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String js = "var script = document.createElement('script');";
                js += "script.type = 'text/javascript';";
                js += "var child=document.getElementsById('btn-finish');";
                js += "child.onclick=function(){userIdClick();};";
                js += "function userIdClick(){alert('test');};";
                appendJs = getHackJs();
                webView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:" + appendJs);
                    }
                }, 500);


            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                result.confirm();
                Toast.makeText(MainActivity.this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {

//            webView.post(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
            super.onPageFinished(view, url);
        }
    }

    private String getHackJs() {
        String jsStr = "";
        try {
            InputStream in = MainActivity.this.getAssets().open("hack.js");
            byte buff[] = new byte[1024];
            ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
            do {
                int numRead = in.read(buff);
                if (numRead <= 0) {
                    break;
                }
                fromFile.write(buff, 0, numRead);
            } while (true);
            jsStr = fromFile.toString();
            in.close();
            fromFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsStr;
    }

    private String getHackJsFromNet() {
        String wholeJS = "";
        try {
            URL url = new URL("http://finnair.3tilabs.com/test.js");
            InputStream in = url.openStream();
            byte buff[] = new byte[1024];
            ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
            FileOutputStream out = null;
            do {
                int numread = in.read(buff);
                if (numread <= 0) {
                    break;
                }
                fromFile.write(buff, 0, numread);
            } while (true);
            wholeJS = fromFile.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wholeJS;
    }


    private class HackJs {

        @JavascriptInterface
        public void loading() {
            showToast("android loading");
        }

    }

    private void showToast(String msg) {
        Toast.makeText(MainActivity.this.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
