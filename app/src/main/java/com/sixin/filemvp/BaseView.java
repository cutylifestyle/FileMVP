package com.sixin.filemvp;

public interface BaseView<T> {
    //TODO 对比TODO-MVP 该方法在只有activity的情况下，用处不大
    void setPresenter(T presenter);

    //TODO 添加该方法比较合适
    boolean isActive();
}
