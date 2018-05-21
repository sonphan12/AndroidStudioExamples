package com.example.lap10255.examplerxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    EditText edtInput;
    Button btnEmit;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
    }

    private void addControls() {
        edtInput = findViewById(R.id.edtInput);
        btnEmit = findViewById(R.id.btnEmit);
        txtResult = findViewById(R.id.txtResult);

        btnEmit.setOnClickListener(v -> {
            // Return result to default
            txtResult.setText(R.string.result);
            // Put input to array
            String input = edtInput.getText().toString();
            String[] inputArray = input.split(",");
            // Create observable
            Observable observable = Observable.just(Arrays.asList(inputArray))
                    .flatMap(Observable::fromIterable)
                    .map(element -> Integer.parseInt(element.trim()))
                    .filter(element -> element % 2 == 0)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
            // Create observer
            Observer observer = new Observer() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Object o) {
                    // Update UI
                    String text = txtResult.getText().toString();
                    text = text + " " + String.valueOf(o) + ",";
                    txtResult.setText(text);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {
                    // Remove the last redundant comma
                    String text = txtResult.getText().toString();
                    text = text.substring(0, text.length() - 1);
                    txtResult.setText(text);
                }
            };

            // subscribe observer to observable
            observable.subscribe(observer);

        });
    }

}