package com.techidea.sgsb.base;

/**
 * Created by sam on 2018/5/28.
 */

public interface BaseView<T extends BasePresenter>{

    void setPresenter(T presenter);
}
