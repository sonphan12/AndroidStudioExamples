package com.example.lap10255.exampleeventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

public class AnotherActivity extends AppCompatActivity {
    EditText edtName;
    EditText edtAge;
    Button btnOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        addControls();
        addListeners();
    }

    private void addControls() {
        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        btnOk = findViewById(R.id.btnOk);
    }

    private void addListeners() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEvent userEvent = new UserEvent(edtName.getText().toString(), edtAge.getText().toString());
                EventBus.getDefault().postSticky(userEvent);
                finish();
            }
        });
    }

}
