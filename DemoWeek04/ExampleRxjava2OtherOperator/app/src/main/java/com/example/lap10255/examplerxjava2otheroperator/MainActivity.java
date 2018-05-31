package com.example.lap10255.examplerxjava2otheroperator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    TextView txtResult;
    Button btnTakeUntil;
    Button btnRepeatWhen;
    Button btnRetryWhen;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compositeDisposable = new CompositeDisposable();

        addControls();
    }

    private void addControls() {
        txtResult = findViewById(R.id.txtResult);
        btnTakeUntil = findViewById(R.id.btnTakeUntil);
        btnRepeatWhen = findViewById(R.id.btnRepeatWhen);
        btnRetryWhen = findViewById(R.id.btnRetryWhen);

        btnTakeUntil.setOnClickListener(view -> {
            txtResult.setText("");
            compositeDisposable.clear();
            compositeDisposable.add(Observable.interval(1, TimeUnit.SECONDS)
                    .takeUntil(value -> value >= 5)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(value -> txtResult.append(String.valueOf(value + "\n")),
                            Throwable::printStackTrace,
                            () -> txtResult.append("Complete!")));
        });

        btnRepeatWhen.setOnClickListener(view -> {
                    txtResult.setText("");
                    compositeDisposable.clear();
                    compositeDisposable.add(Observable.just("repeat")
                            .repeatWhen(completed -> {
                                Log.d("REPEAT WHEN", Thread.currentThread().getName());
                                return completed.delay(2, TimeUnit.SECONDS);
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(str -> txtResult.append(str + "\n"), Throwable::printStackTrace,
                                    () -> txtResult.append("Complete!")));
                }
        );
        btnRetryWhen.setOnClickListener(view -> {
            txtResult.setText("");
            final int[] i = {0};
            compositeDisposable.clear();
            compositeDisposable.add(
                    Observable.create(emitter -> {
                        emitter.onNext(1 / i[0]);
                        emitter.onComplete();
                    })
                            .retryWhen(throwableObservable -> {
                                i[0]++;
                                return throwableObservable;
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(val -> txtResult.append(String.valueOf(val) + "\n"),
                                    Throwable::printStackTrace,
                                    () -> txtResult.append("Complete!\n")));
        });

    }
}
