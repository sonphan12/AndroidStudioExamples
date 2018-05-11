package com.example.lap10255.sharedpreferencesexample;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.edtString)EditText edtString;
    @BindView(R.id.edtInt)EditText edtInt;
    @BindView(R.id.edtFloat)EditText edtFloat;
//    @BindView(R.id.btnSave)Button btnSave;

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String s = sharedPreferences.getString("string", "");
        int i = sharedPreferences.getInt("int", 0);
        float f = sharedPreferences.getFloat("float", 0.0f);

        edtString.setText(s);
        edtInt.setText(String.valueOf(i));
        edtFloat.setText(String.valueOf(f));
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnSave)
    public void clickedSave(View v){
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("string", edtString.getText().toString());
        editor.putInt("int", Integer.parseInt(edtInt.getText().toString()));
        editor.putFloat("float", Float.parseFloat(edtFloat.getText().toString()));
        editor.apply();
    }
}
