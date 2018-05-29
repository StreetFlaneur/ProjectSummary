package com.techidea.sgsb.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.techidea.library.widget.ProgressBarWebView;
import com.techidea.sgsb.R;
import com.techidea.sgsb.constant.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sam on 2018/1/31.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.progress_webview)
    ProgressBarWebView webView;

    public static HomeFragment newInstance(String content) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PUTEXTRA_CONTENT, content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String content = getArguments().getString(Constants.PUTEXTRA_CONTENT);
        String url = "http://promotion.metro.com.cn/201804-shang-hai-pu-tuo-shang-hai-min-xing-shang-hai-pu-dong-shang-hai-jia-ding-shang-hai-jin-shan-shang-hai-qing-pu-shang-hai-song-jiang/?cid=cn:store:sm:wechat:metromail_201804";
//        String url = "https://cn.bing.com/";
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.loadUrl(url);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        // TODO: 2018/1/31 页面数据重新加载
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
