package com.nile.kmooc.view;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nile.kmooc.util.observer.MainThreadObservable;
import com.nile.kmooc.util.observer.Observable;
import com.nile.kmooc.util.observer.SubscriptionManager;

public abstract class ViewHoldingPresenter<V> implements Presenter<V> {

    @Nullable
    private V view;

    @NonNull
    private final SubscriptionManager viewSubscriptionManager = new SubscriptionManager();

    @Override
    @CallSuper
    public void attachView(@NonNull V view) {
        this.view = view;
    }

    @Override
    @CallSuper
    public void detachView() {
        this.view = null;
        viewSubscriptionManager.unsubscribeAll();
    }

    @Override
    @CallSuper
    public void destroy() {
        viewSubscriptionManager.unsubscribeAll();
    }

    @NonNull
    public <T> Observable<T> observeOnView(@NonNull Observable<T> observable) {
        return viewSubscriptionManager.wrap(new MainThreadObservable<>(observable));
    }

    @Nullable
    public V getView() {
        return view;
    }

}
