package com.library.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.techidea.library.utils.StatusBarUtil;
import com.techidea.library.widget.GradationScrollView;
import com.techidea.library.widget.StatusView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sam on 2017/10/7.
 */

public class ImageHeadActivity extends AppCompatActivity implements GradationScrollView.ScrollViewListener {

    @BindView(R.id.scrollView)
    GradationScrollView scrollView;
    @BindView(R.id.toolbar_common)
    Toolbar toolbar;
    @BindView(R.id.toolbar_common_title)
    TextView toolbarTitle;
    @BindView(R.id.statusview)
    StatusView statusView;

    int endOff = 0;
    private boolean menuActionIsWhite = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagehead);
        ButterKnife.bind(this);
        initToolbar();
        endOff = getResources().getDimensionPixelSize(R.dimen.image_head_height)
                - getResources().getDimensionPixelSize(R.dimen.toolbar_height)
                - StatusBarUtil.getStatusBarHeight(this);
        StatusBarUtil.setFullToStatusBar(this);
        StatusBarUtil.setStatusBarFontIconDark(this, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            statusView.setBackgroundColor(getResources().getColor(R.color.status_grey));
        }
        setBarAlpha(0);
        scrollView.setScrollViewListener(this);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.mipmap.btn_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateToolbarMenu() {
        if (menuActionIsWhite) {
            toolbar.setNavigationIcon(R.mipmap.btn_back_white);
        } else {
            toolbar.setNavigationIcon(R.mipmap.btn_back_grey);
        }
        invalidateOptionsMenu();
    }

    @Override
    public void onScrollChanged(int x, int y, int oldl, int oldt) {
        if (y <= 0) {
            setBarAlpha(0);
            menuActionIsWhite = true;
        } else if (y > 0 && y <= endOff) {
            float pesent = (float) y / endOff;
            int alpha = Math.round(pesent * 255);
            setBarAlpha(alpha);
            menuActionIsWhite = true;
        } else {
            setBarAlpha(255);
            menuActionIsWhite = false;
        }
        updateToolbarMenu();
    }

    private void setBarAlpha(int alpha) {
        statusView.getBackground().mutate().setAlpha(alpha);
        toolbar.getBackground().mutate().setAlpha(alpha);
    }
}
