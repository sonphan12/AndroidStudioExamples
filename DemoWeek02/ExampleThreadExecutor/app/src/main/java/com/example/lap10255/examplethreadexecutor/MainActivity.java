package com.example.lap10255.examplethreadexecutor;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    TextView txtResult;
    Button btnExecute;
    String result;
    Handler mHandler;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);
        btnExecute = findViewById(R.id.btnExecute);
        result = "";

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    txtResult.setText(result);
                }
            }
        };

        btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = "";
                ExecutorService executor = Executors.newFixedThreadPool(5);
                for (int i = 0; i < 10; i++) {
                    final int finalI = i;
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Random random = new Random();
                                Thread.sleep(random.nextInt(1000) + 1);
                                result += "Task " + finalI + " is done!\n";
                                mHandler.sendEmptyMessage(0);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                executor.shutdown();

            }
        });
    }


}
