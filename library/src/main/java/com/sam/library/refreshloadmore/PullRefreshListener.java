package com.sam.library.refreshloadmore;

/**
 * Created by zc on 2017/9/15.
 */

public interface PullRefreshListener {
    /**
     * 下拉中
     *
     * @param refreshLayout
     * @param fraction
     */
    void onPullingDown(RefreshLoadMoreLayout refreshLayout, float fraction);

    /**
     * 上拉
     */
    void onPullingUp(RefreshLoadMoreLayout refreshLayout, float fraction);

    /**
     * 下拉松开
     *
     * @param refreshLayout
     * @param fraction
     */
    void onPullDownReleasing(RefreshLoadMoreLayout refreshLayout, float fraction);

    /**
     * 上拉松开
     */
    void onPullUpReleasing(RefreshLoadMoreLayout refreshLayout, float fraction);

    /**
     * 刷新中。。。
     */
    void onRefresh(RefreshLoadMoreLayout refreshLayout);

    /**
     * 加载更多中
     */
    void onLoadMore(RefreshLoadMoreLayout refreshLayout);

    /**
     * 手动调用finishRefresh或者finishLoadmore之后的回调
     */
    void onFinishRefresh();

    void onFinishLoadMore();

    /**
     * 正在刷新时向上滑动屏幕，刷新被取消
     */
    void onRefreshCanceled();

    /**
     * 正在加载更多时向下滑动屏幕，加载更多被取消
     */
    void onLoadmoreCanceled();
}
