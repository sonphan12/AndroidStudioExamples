package com.sonphan12.exampleclean.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApplySchedulers {
    public static <T> ObservableTransformer<T, T> applyScheduler() {
        return observable -> observable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
