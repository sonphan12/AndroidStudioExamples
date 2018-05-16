package com.example.lap10255.examplehandlerlooper;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button btnDownload, btnCaculate;
    Handler updateUIHandler;
    ProgressBar progressDownload;
    EditText edtA, edtB;
    TextView txtResult;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownload = findViewById(R.id.btnDownload);
        progressDownload = findViewById(R.id.progressDownload);
        btnCaculate = findViewById(R.id.btnCalculate);
        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        txtResult = findViewById(R.id.txtResult);

        btnCaculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(edtA.getText().toString());
                int b = Integer.parseInt(edtB.getText().toString());
                txtResult.setText(String.valueOf(a + b));
            }
        });

        updateUIHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    progressDownload.setProgress((Integer) msg.obj);
                }
            }
        };

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread workerThread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        int totalPercent = 100;
                        int currentPercent = 0;
                        Random random = new Random();
                        while (currentPercent < totalPercent) {
                            try {
                                Thread.sleep(100);
                                currentPercent += random.nextInt(5) + 1;
                                if (currentPercent > totalPercent) {
                                    currentPercent = totalPercent;
                                }
                                Message message = new Message();

                                message.what = 0;
                                message.obj = currentPercent;
                                updateUIHandler.sendMessage(message);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                workerThread.start();
            }
        });
    }
}
