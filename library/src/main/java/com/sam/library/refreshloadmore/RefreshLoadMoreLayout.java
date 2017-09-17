package com.sam.library.refreshloadmore;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.sam.library.refreshloadmore.bottomview.DefaultBottomView;
import com.sam.library.refreshloadmore.headview.DefaultHeadView;

/**
 * Created by zc on 2017/9/14.
 */

public class RefreshLoadMoreLayout extends RelativeLayout
        implements NestedScrollingChild {

    private static final String TAG = RefreshLoadMoreLayout.class.getCanonicalName();

    //VelocityX,VelocityY
    private float vx, vy;
    private VelocityTracker mVelocityTracker;//计算速度

    private View mChildView;
    private FrameLayout mHeadFrameLayout;
    private IHeadView mHeadView;
    private FrameLayout mBottomFrameLayout;
    private IBottomView mBottomView;
    //整个头部
    private FrameLayout mExtraHeadLayout;

    private int mMaxHeadHeight;
    private int mHeadHeight;
    private int mMaxBottomHeight;
    private int mBottomHeight;

    //允许的越界回弹的高度
    protected float mOverScrollHeight;

    private float mLastFocusX;
    private float mLastFocusY;
    private float mDownFocusX;
    private float mDownFocusY;

    protected boolean isRefreshVisible = false;
    protected boolean isLoadingVisible = false;
    protected boolean isRefreshing = false;
    protected boolean isLoadingMore = false;
    //是否需要加载更多,默认需要
    protected boolean enableLoadmore = true;
    //是否需要下拉刷新,默认需要
    protected boolean enableRefresh = true;

    //是否在越界回弹的时候显示下拉图标
    protected boolean isOverScrollTopShow = true;
    //是否在越界回弹的时候显示上拉图标
    protected boolean isOverScrollBottomShow = true;

    //是否隐藏刷新控件,开启越界回弹模式(开启之后刷新控件将隐藏)
    protected boolean isPureScrollModeOn = false;

    //是否自动加载更多
    protected boolean autoLoadMore = false;

    //是否开启悬浮刷新模式
    protected boolean floatRefresh = false;

    //是否允许进入越界回弹模式
    protected boolean enableOverScroll = true;

    //是否在刷新或者加载更多后保持状态
    protected boolean enableKeepIView = true;

    //是否在越界且处于刷新时直接显示顶部
    protected boolean showRefreshingWhenOverScroll = true;

    //是否在越界且处于加载更多时直接显示底部
    protected boolean showLoadingWhenOverScroll = true;

    private final int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    private IDecorator decorator;

    private OnGestureListener onGestureListener;
    private final NestedScrollingChildHelper childHelper;
    private PullRefreshListener pullListener;

    private CoContext cp;

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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mChildView = getChildAt(3);
        cp.init();
        decorator = new OverScrollDecorator(cp, new RepreshProcessor(cp));
        initGestureListener();
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
                decorator.onFingerDown(ev);
            }

            @Override
            public void onScroll(MotionEvent downEvent, MotionEvent currentEvent, float distanceX, float distanceY) {
                decorator.onFingerScroll(downEvent, currentEvent, distanceX, distanceY, vx, vy);
            }

            @Override
            public void onUp(MotionEvent ev, boolean isFling) {
                decorator.onFingerUp(ev, isFling);
            }

            @Override
            public void onFling(MotionEvent downEvent, MotionEvent upEvent, float velocityX, float velocityY) {
                decorator.onFingerFling(downEvent, upEvent, velocityX, velocityY);
            }
        };
    }

    public void addHeadView() {
        FrameLayout headViewLayout = new FrameLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        layoutParams.addRule(ALIGN_PARENT_TOP);

        this.addView(headViewLayout, layoutParams);
        mHeadFrameLayout = headViewLayout;

        if (mHeadFrameLayout == null) {
            setHeadView(new DefaultHeadView(getContext()));
        }
    }

    public void addBottomView() {
        FrameLayout bottomView = new FrameLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);

        this.addView(bottomView, layoutParams);
        mBottomFrameLayout = bottomView;

        if (mBottomFrameLayout == null) {
            setBottomView(new DefaultBottomView(getContext()));
        }
    }

    public void setHeadView(IHeadView headView) {
        if (mHeadFrameLayout != null) {
            mHeadFrameLayout.removeAllViewsInLayout();
            mHeadFrameLayout.addView(headView.getView());
            this.mHeadView = headView;
        }
    }

    public void setBottomView(IBottomView bottomView) {
        if (mBottomFrameLayout != null) {
            mBottomFrameLayout.removeAllViewsInLayout();
            mBottomFrameLayout.addView(bottomView.getView());
            mBottomView = bottomView;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean consume = decorator.dispatchTouchEvent(event);
        return consume;
       /* if (mVelocityTracker == null) {
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
        return super.dispatchTouchEvent(event);*/
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = decorator.interceptTouchEvent(ev);
        return intercept || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean consume = decorator.dealTouchEvent(event);
        return consume || super.onTouchEvent(event);
    }

    private void detectGesture(MotionEvent ev, OnGestureListener listener) {
        final int action = ev.getAction();
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
        final boolean pointerUp = (action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP;
        final int skip = pointerUp ? ev.getActionIndex() : -1;
        float sumX = 0, sumY = 0;
        final int count = ev.getPointerCount();
        for (int i = 0; i < count; i++) {
            if (skip == i) continue;
            sumX += ev.getX(i);
            sumY += ev.getY(i);
        }
        final int div = pointerUp ? count - 1 : count;
        final float focusX = sumX / div;
        final float focusY = sumY / div;
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

    }

    public class CoContext {

        private AnimaProcess animaProcess;
        private final static int PULLING_TOP_DOWN = 0;
        private final static int PULLING_BOTTOM_UP = 1;
        private int state = PULLING_TOP_DOWN;

        private static final int EX_MODE_NORMAL = 0;
        private static final int EX_MODE_FIXED = 1;
        private int exHeadMode = EX_MODE_NORMAL;

        public CoContext() {
            this.animaProcess = new AnimaProcess(this);
        }

        public void init() {
            if (isPureScrollModeOn) {
                if (mHeadFrameLayout != null) mHeadFrameLayout.setVisibility(GONE);
                if (mBottomFrameLayout != null) mBottomFrameLayout.setVisibility(GONE);
            }
        }

        public AnimaProcess getAnimProcessor() {
            return animaProcess;
        }

        public boolean isEnableKeepIView() {
            return enableKeepIView;
        }

        public boolean showRefreshingWhenOverScroll() {
            return showRefreshingWhenOverScroll;
        }

        public boolean showLoadingWhenOverScroll() {
            return showLoadingWhenOverScroll;
        }

        public float getMaxHeadHeight() {
            return mMaxHeadHeight;
        }

        public int getHeadHeight() {
            return (int) mHeadHeight;
        }

        public int getExtraHeadHeight() {
            return mExtraHeadLayout.getHeight();
        }

        public int getMaxBottomHeight() {
            return (int) mMaxBottomHeight;
        }

        public int getBottomHeight() {
            return (int) mBottomHeight;
        }

        public int getOsHeight() {
            return (int) mOverScrollHeight;
        }

        public View getTargetView() {
            return mChildView;
        }

        public View getHeader() {
            return mHeadFrameLayout;
        }

        public View getFooter() {
            return mBottomFrameLayout;
        }

        public int getTouchSlop() {
            return mTouchSlop;
        }

        public void resetHeaderView() {
            if (mHeadView != null) mHeadView.reset();
        }

        public void resetBottomView() {
            if (mBottomView != null) mBottomView.reset();
        }

        public View getExHead() {
            return mExtraHeadLayout;
        }

        public void setExHeadNormal() {
            exHeadMode = EX_MODE_NORMAL;
        }

        public void setExHeadFixed() {
            exHeadMode = EX_MODE_FIXED;
        }

        public boolean isExHeadNormal() {
            return exHeadMode == EX_MODE_NORMAL;
        }

        public boolean isExHeadFixed() {
            return exHeadMode == EX_MODE_FIXED;
        }

        /**
         * 主动刷新、加载更多、结束
         */
        public void startRefresh() {
            post(new Runnable() {
                @Override
                public void run() {
                    setStatePTD();
                    if (!isPureScrollModeOn && mChildView != null) {
                        setRefreshing(true);
                        animaProcess.animHeadToRefresh();
                    }
                }
            });
        }

        public void startLoadMore() {
            post(new Runnable() {
                @Override
                public void run() {
                    setStatePBU();
                    if (!isPureScrollModeOn && mChildView != null) {
                        setLoadingMore(true);
                        animaProcess.animBottomToLoad();
                    }
                }
            });
        }

        public void finishRefreshing() {
            onFinishRefresh();
        }

        public void finishRefreshAfterAnim() {
            if (mChildView != null) {
                animaProcess.animHeadBack(true);
            }
        }

        public void finishLoadmore() {
            onFinishLoadMore();
            if (mChildView != null) {
                animaProcess.animBottomBack(true);
            }
        }

        public boolean enableOverScroll() {
            return enableOverScroll;
        }

        public boolean allowPullDown() {
            return enableRefresh || enableOverScroll;
        }

        public boolean allowPullUp() {
            return enableLoadmore || enableOverScroll;
        }

        public boolean enableRefresh() {
            return enableRefresh;
        }

        public boolean enableLoadmore() {
            return enableLoadmore;
        }

        public boolean allowOverScroll() {
            return (!isRefreshVisible && !isLoadingVisible);
        }

        public boolean isRefreshVisible() {
            return isRefreshVisible;
        }

        public boolean isLoadingVisible() {
            return isLoadingVisible;
        }

        public void setRefreshVisible(boolean visible) {
            isRefreshVisible = visible;
        }

        public void setLoadVisible(boolean visible) {
            isLoadingVisible = visible;
        }

        public void setRefreshing(boolean refreshing) {
            isRefreshing = refreshing;
        }

        public boolean isRefreshing() {
            return isRefreshing;
        }

        public boolean isLoadingMore() {
            return isLoadingMore;
        }

        public void setLoadingMore(boolean loadingMore) {
            isLoadingMore = loadingMore;
        }

        public boolean isOpenFloatRefresh() {
            return floatRefresh;
        }

        public boolean autoLoadMore() {
            return autoLoadMore;
        }

        public boolean isPureScrollModeOn() {
            return isPureScrollModeOn;
        }

        public boolean isOverScrollTopShow() {
            return isOverScrollTopShow;
        }

        public boolean isOverScrollBottomShow() {
            return isOverScrollBottomShow;
        }

        public void onPullingDown(float offsetY) {
            pullListener.onPullingDown(RefreshLoadMoreLayout.this, offsetY / mHeadHeight);
        }

        public void onPullingUp(float offsetY) {
            pullListener.onPullingUp(RefreshLoadMoreLayout.this, offsetY / mBottomHeight);
        }

        public void onRefresh() {
            pullListener.onRefresh(RefreshLoadMoreLayout.this);
        }

        public void onLoadMore() {
            pullListener.onLoadMore(RefreshLoadMoreLayout.this);
        }

        public void onFinishRefresh() {
            pullListener.onFinishRefresh();
        }

        public void onFinishLoadMore() {
            pullListener.onFinishLoadMore();
        }

        public void onPullDownReleasing(float offsetY) {
            pullListener.onPullDownReleasing(RefreshLoadMoreLayout.this, offsetY / mHeadHeight);
        }

        public void onPullUpReleasing(float offsetY) {
            pullListener.onPullUpReleasing(RefreshLoadMoreLayout.this, offsetY / mBottomHeight);
        }

        public boolean dispatchTouchEventSuper(MotionEvent ev) {
            return RefreshLoadMoreLayout.super.dispatchTouchEvent(ev);
        }

        public void onRefreshCanceled() {
            pullListener.onRefreshCanceled();
        }

        public void onLoadmoreCanceled() {
            pullListener.onLoadmoreCanceled();
        }

        public void setStatePTD() {
            state = PULLING_TOP_DOWN;
        }

        public void setStatePBU() {
            state = PULLING_BOTTOM_UP;
        }

        public boolean isStatePTD() {
            return PULLING_TOP_DOWN == state;
        }

        public boolean isStatePBU() {
            return PULLING_BOTTOM_UP == state;
        }

        private boolean prepareFinishRefresh = false;
        private boolean prepareFinishLoadMore = false;

        public boolean isPrepareFinishRefresh() {
            return prepareFinishRefresh;
        }

        public boolean isPrepareFinishLoadMore() {
            return prepareFinishLoadMore;
        }

        public void setPrepareFinishRefresh(boolean prepareFinishRefresh) {
            this.prepareFinishRefresh = prepareFinishRefresh;
        }

        public void setPrepareFinishLoadMore(boolean prepareFinishLoadMore) {
            this.prepareFinishLoadMore = prepareFinishLoadMore;
        }
    }
}
