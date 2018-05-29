package com.techidea.sgsb.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.techidea.sgsb.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sam on 2018/5/29.
 */

public class AnimatorActivity extends AppCompatActivity {

    private static final String TAG = "Animator";

    @BindView(R.id.text_animator)
    TextView tvAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animator_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_start)
    void btStart() {
//        objectAnimator();
        valueAnimator();
    }

    private void valueAnimator() {
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setDuration(300);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                Log.d(TAG, "cuurent value is " + currentValue);
            }
        });
        anim.start();
    }

    private void objectAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvAnimator, "rotation", 0f, 360f);
        animator.setDuration(5000);
        animator.start();
    }
}
