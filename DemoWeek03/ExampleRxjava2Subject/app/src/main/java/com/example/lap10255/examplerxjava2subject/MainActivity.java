package com.example.lap10255.examplerxjava2subject;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtResult;
    private Button btnColdObservable;
    private Button btnAsyncSubject;
    private Button btnBehaviourSubject;
    private Button btnPublishSubject;
    private Button btnReplaySubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);
        btnColdObservable = findViewById(R.id.btnColdObservable);
        btnColdObservable.setOnClickListener(this);
        btnPublishSubject = findViewById(R.id.btnPublishSubject);
        btnPublishSubject.setOnClickListener(this);
        btnAsyncSubject = findViewById(R.id.btnAsyncSubject);
        btnAsyncSubject.setOnClickListener(this);
        btnBehaviourSubject = findViewById(R.id.btnBehaviourSubject);
        btnBehaviourSubject.setOnClickListener(this);
        btnReplaySubject = findViewById(R.id.btnReplaySubject);
        btnReplaySubject.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        txtResult.setText("");
        switch (v.getId()) {
            case R.id.btnColdObservable:
                doColdObservable();
                break;
            case R.id.btnAsyncSubject:
                doAsyncSubject();
                break;
            case R.id.btnBehaviourSubject:
                doBehaviourSubject();
                break;
            case R.id.btnPublishSubject:
                doPublishSubject();
                break;
            case R.id.btnReplaySubject:
                doReplaySubject();
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void doColdObservable() {
        Observable<Integer> coldObservable = Observable.create((ObservableEmitter<Integer> emitter) -> {
            for (int i = 0; i < 4; i++) {
                emitter.onNext(i);
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        coldObservable.subscribe(value -> txtResult.append("Subscriber 1: " + value + "\n")
                , e -> {
                });
        coldObservable.subscribe(value -> txtResult.append("Subscriber 2: " + value + "\n")
                , e -> {
                });

    }

    @SuppressLint("CheckResult")
    private void doAsyncSubject() {
        Observable<Integer> coldObservable = Observable.create((ObservableEmitter<Integer> emitter) -> {
            for (int i = 0; i < 4; i++) {
                emitter.onNext(i);
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();
        coldObservable.subscribe(asyncSubject);
        // Subscriber 1
        asyncSubject.subscribe(value -> txtResult.append("Subscriber 1: " + value + "\n")
                , e -> {
                });
        // Subscriber 2
        asyncSubject.subscribe(value -> txtResult.append("Subscriber 2: " + value + "\n")
                , e -> {
                });

        // Both subscriber receiver the last value (3) at once

    }

    // BehaviourSubject emits the most recently item at the time of subscription or a default item
    // if none has been emitted and then continues the sequence until complete.
    @SuppressLint("CheckResult")
    private void doBehaviourSubject() {
        Observable<Long> coldObservable = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        BehaviorSubject<Long> behaviorSubject = BehaviorSubject.createDefault(-1L);
        // Subscribe the subscriber before subscribe coldObservable
        // So BehaviourSubject will emit default value (-1) first
        // Subscriber 1
        behaviorSubject.subscribe(value -> txtResult.append("Subscriber 1: " + value + "\n")
                , e -> {
                });
        // Subscriber 2
        behaviorSubject.subscribe(value -> txtResult.append("Subscriber 2: " + value + "\n")
                , e -> {
                });
        coldObservable.subscribe(behaviorSubject);

        // After this, Behaviour Subject continue to emit
    }

    // PublishSubject is much similar to BehaviourSubject except that it emits only those items
    // which are emitted after the subscription. Also, It does NOT give any default value.
    @SuppressLint("CheckResult")
    private void doPublishSubject() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();

        publishSubject.subscribe(value -> txtResult.append("Subscriber 1: " + value + "\n")
                , e -> {
                });
        publishSubject.onNext(1);
        publishSubject.onNext(2);
        publishSubject.onNext(3);
        publishSubject.subscribe(value -> txtResult.append("Subscriber 2: " + value + "\n")
                , e -> {
                });
        publishSubject.onNext(4);
        // Subscriber 2 only receive value 4
    }
    // It emits all the emitted items to the subscribers regardless of when the subscribers
    // subscribes and then continues the sequence. There are also versions of ReplySubject that
    // will throw away the items if the buffer size gets filled with items or specified timespan gets passed.
    @SuppressLint("CheckResult")
    private void doReplaySubject() {

        ReplaySubject<Integer> replaySubject = ReplaySubject.create();
        replaySubject.subscribe(value -> txtResult.append("Subscriber 1: " + value + "\n")
                , e -> {
                });
        replaySubject.onNext(1);
        replaySubject.onNext(2);
        replaySubject.onNext(3);

        replaySubject.subscribe(value -> txtResult.append("Subscriber 2: " + value + "\n")
                , e -> {
                });
        replaySubject.onNext(4);

        // Subscriber 2 will also receive value 1, 2 and 3

    }
}
