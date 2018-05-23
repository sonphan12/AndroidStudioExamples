package com.example.lap10255.examplegreendao;


import io.reactivex.Observable;

public class SearchObservable {
    public static Observable<String> search(final String query) {
        return Observable.create(emitter -> emitter.onNext(query));
    }
}
