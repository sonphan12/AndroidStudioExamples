package com.example.lap10255.exampleasynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button btnDownload;
    private Button btnCalculate;
    private EditText edtA, edtB;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDownload = findViewById(R.id.btnDownload);
        btnCalculate = findViewById(R.id.btnCalculate);
        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        txtResult = findViewById(R.id.txtResult);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadTask(MainActivity.this).execute();
                btnDownload.setEnabled(false);
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(edtA.getText().toString());
                int b = Integer.parseInt(edtB.getText().toString());
                txtResult.setText(String.valueOf(a + b));
            }
        });


    }


}
