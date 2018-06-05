package com.example.lap10255.examplerxbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AnotherActivity extends AppCompatActivity {
    EditText edtName;
    EditText edtAge;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        btnOk = findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(edtName.getText().toString(), edtAge.getText().toString());
                ((MyApplication)getApplication()).getBus().send(user);
                finish();
            }
        });
    }
}
