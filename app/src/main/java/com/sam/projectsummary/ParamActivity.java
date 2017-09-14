package com.sam.projectsummary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.sam.library.refreshloadmore.RefreshLoadMoreLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zc on 2017/9/13.
 */

public class ParamActivity extends AppCompatActivity {

    @BindView(R.id.refreloadmore_layout)
    RefreshLoadMoreLayout refreshLoadMoreLayout;

    private static final String TAG = ParamActivity.class.getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshloadmore);
        ButterKnife.bind(this);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.d(TAG, String.valueOf(metrics.widthPixels));
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        Log.d(TAG, String.valueOf(screenWidth));
    }
}
