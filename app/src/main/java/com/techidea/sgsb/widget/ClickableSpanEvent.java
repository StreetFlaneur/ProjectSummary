package com.techidea.sgsb.widget;

import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by sam on 2018/1/17.
 */

public class ClickableSpanEvent extends ClickableSpan implements View.OnClickListener {

    private final View.OnClickListener mClickListener;

    public ClickableSpanEvent(View.OnClickListener onClickListener) {
        this.mClickListener = onClickListener;
    }

    @Override
    public void onClick(View widget) {
        this.mClickListener.onClick(widget);
    }
}
