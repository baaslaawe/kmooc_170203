package com.nile.kmooc.view;


import android.support.annotation.NonNull;

public interface Presenter<V> {
    void attachView(@NonNull V view);
    void detachView();
    void destroy();
}
