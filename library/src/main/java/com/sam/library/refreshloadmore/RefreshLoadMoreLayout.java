package com.sam.library.refreshloadmore;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by zc on 2017/9/14.
 */

public class RefreshLoadMoreLayout extends RelativeLayout
        implements NestedScrollingChild {

    private static final String TAG = RefreshLoadMoreLayout.class.getCanonicalName();

    //VelocityX,VelocityY
    private float vx, vy;
    private VelocityTracker mVelocityTracker;//计算速度

    private FrameLayout mHeadFrameLayout;
    private FrameLayout mFoorFrameLayout;

    private int mHeadHeight;
    private int mBottomHeight;

    private float mLastFocusX;
    private float mLastFocusY;
    private float mDownFocusX;
    private float mDownFocusY;

    private final int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    private OnGestureListener onGestureListener;

    private final NestedScrollingChildHelper childHelper;

    public RefreshLoadMoreLayout(Context context) {
        this(context, null);
    }

    public RefreshLoadMoreLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLoadMoreLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        addHeadView();
        addBottomView();
        this.childHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }


    private void init() {
        initGestureListener();
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        this.childHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return this.childHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        childHelper.stopNestedScroll();
    }

    private void initGestureListener() {
        onGestureListener = new OnGestureListener() {
            @Override
            public void onDown(MotionEvent ev) {

            }

            @Override
            public void onScroll(MotionEvent downEvent, MotionEvent currentEvent, float distanceX, float distanceY) {

            }

            @Override
            public void onUp(MotionEvent ev, boolean isFling) {

            }

            @Override
            public void onFling(MotionEvent downEvent, MotionEvent upEvent, float velocityX, float velocityY) {

            }
        };
    }

    public void addHeadView() {
        FrameLayout headViewLayout = new FrameLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        layoutParams.addRule(ALIGN_PARENT_TOP);


    }

    public void addBottomView() {

    }

    public void setHeadView() {

    }

    public void setBottomView() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastFocusX = mDownFocusX = event.getX();
                mLastFocusY = mDownFocusY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                float upX = event.getX();
                float upY = event.getY();
                Log.d(TAG, "up x " + upX);
                Log.d(TAG, "up y " + upY);
                final float x1 = mVelocityTracker.getXVelocity();
                final float y1 = mVelocityTracker.getYVelocity();
                Log.d(TAG, "speed x " + x1);
                Log.d(TAG, "speed y " + y1);
                final float distance = mDownFocusY - upY;
                if (distance > 0 && Math.abs(distance) > mTouchSlop) {
                    Log.d(TAG, "UP");
                    return true;
                } else if (distance < 0 && Math.abs(distance) < mTouchSlop) {
                    Log.d(TAG, "DOWN");
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}
