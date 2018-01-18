package com.sam.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sam.library.R;

/**
 * Created by sam on 2018/1/15.
 */

public class DMTabButton extends RelativeLayout{
    private ImageView image;
    private TextView tab_button;
    private View tab_tip;
    private TextView unread_count;
    private boolean isChecked = false;

    public DMTabButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLayout(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public DMTabButton(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public DMTabButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context, attrs);
        // TODO Auto-generated constructor stub
    }

    private void initLayout(Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub
        View contentView = LayoutInflater.from(context).inflate(R.layout.tab_bottom_button_item, this);
        image = (ImageView) contentView.findViewById(R.id.iv_tab_item_icon);
        tab_button = (TextView) contentView.findViewById(R.id.tv_tab_item_icon);
        tab_tip = contentView.findViewById(R.id.tab_tip);
        unread_count = (TextView) contentView.findViewById(R.id.unread_count);
        TypedArray a = getResources().obtainAttributes(attrs, R.styleable.tab_button);
        Drawable d = a.getDrawable(R.styleable.tab_button_drawableTop);
        String text = a.getString(R.styleable.tab_button_tabtext);
        String attrStr = a.getString(R.styleable.tab_button_drawableTopAttr);
        a.recycle();
        tab_button.setText(text);
        if (d != null) {
            image.setImageDrawable(d);
        } else {
            TypedValue typedValue = new TypedValue();
            getContext().getTheme().resolveAttribute(getResources().getIdentifier(attrStr, "attr", context.getPackageName()),
                    typedValue, true);
            image.setImageResource(typedValue.resourceId);
        }

    }



}
