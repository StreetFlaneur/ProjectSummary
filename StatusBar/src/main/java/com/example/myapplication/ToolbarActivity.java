package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.sam.library.base.BaseActivity;
import com.sam.library.widget.GradationScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sam on 2017/10/7.
 */

public class ToolbarActivity extends BaseActivity
        implements GradationScrollView.ScrollViewListener {

    @BindView(R.id.scrollView)
    GradationScrollView scrollView;
    @BindView(R.id.toolbar_common)
    Toolbar toolbar;
    @BindView(R.id.toolbar_common_title)
    TextView toolbarTitle;
    @BindView(R.id.imageview_logo)
    ImageView imageViewLogo;

    int white = 0xFFFFFFFF;
    int blue = 0xFF3498db;
    int orange = 0xFFf39c12;
    int red = 0xFFe74c3c;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("StatusBar");
        setImmersiveStatusBar(false, white);
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {

    }

    private void scrollPosition() {
        //scrollview 滑动到屏幕指定位置
        int[] location = new int[2];
        imageViewLogo.getLocationOnScreen(location);
        final int y = location[1];
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, y);
            }
        }, 500);
    }

    @OnClick(R.id.button_white)
    void whiteClick() {
        scrollPosition();

//        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
//        toolbarTitle.setTextColor(getResources().getColor(R.color.grey_blue));
//        setImmersiveStatusBar(true, white);
    }

    @OnClick(R.id.button_imagehead)
    void imageHeadClick() {
        startActivity(new Intent(this, ImageHeadActivity.class));
    }

    @OnClick(R.id.button_blue)
    void blueClick() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
        toolbarTitle.setTextColor(getResources().getColor(R.color.white));
        setImmersiveStatusBar(false, blue);
    }

    @OnClick(R.id.button_orange)
    void orangeClick() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.orange));
        toolbarTitle.setTextColor(getResources().getColor(R.color.white));
        setImmersiveStatusBar(false, orange);
    }

    @OnClick(R.id.button_red)
    void redClick() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.red));
        toolbarTitle.setTextColor(getResources().getColor(R.color.white));
        setImmersiveStatusBar(false, red);
    }
}
