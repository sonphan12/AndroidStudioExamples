package com.example.lap10255.internalstorageexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.txtLoad)TextView txtLoad;
    @BindView(R.id.edtSave)EditText edtSave;
    @BindView(R.id.btnLoad)Button btnLoad;
    @BindView(R.id.btnSave)Button btnSave;
    private String fileName = "note.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


    }

    @OnClick({R.id.btnLoad, R.id.btnSave})
    public void buttonClicked(View v) {
        switch (v.getId()){
            case R.id.btnLoad: readData();
            case R.id.btnSave: writeData();
        }
    }


    public void readData(){
        try {
            FileInputStream input = openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder stringBuilder = new StringBuilder();
            String s;
            while ((s = reader.readLine()) != null) {
                stringBuilder.append(s).append("\n");
            }
            txtLoad.setText(stringBuilder.toString());
            input.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private void writeData() {
        try {
            FileOutputStream output = openFileOutput(fileName, MODE_PRIVATE);
            output.write(edtSave.getText().toString().getBytes());
            output.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
