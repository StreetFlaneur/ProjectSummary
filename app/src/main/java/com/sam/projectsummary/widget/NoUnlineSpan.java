package com.sam.projectsummary.widget;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.UnderlineSpan;

/**
 * Created by sam on 2018/1/17.
 */

public class NoUnlineSpan extends UnderlineSpan {

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.parseColor("#3F51B5"));
        ds.setUnderlineText(false);
    }
}
