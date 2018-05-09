package com.example.lap10255.intentflagexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class AActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnToB;
    Button btnToC;
    Button btnToD;
    CheckBox chkAddNewTask;
    CheckBox chkAddClearTask;
    CheckBox chkAddClearTop;
    CheckBox chkAddSingleTop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        btnToB = findViewById(R.id.btnToB);
        btnToC = findViewById(R.id.btnToC);
        btnToD = findViewById(R.id.btnToD);

        chkAddNewTask = findViewById(R.id.chkAddNewTask);
        chkAddClearTask = findViewById(R.id.chkAddClearTask);
        chkAddClearTop = findViewById(R.id.chkAddClearTop);
        chkAddSingleTop = findViewById(R.id.chkAddSingleTop);

        btnToB.setOnClickListener(this);
        btnToC.setOnClickListener(this);
        btnToD.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        int flags = 0;
        if (chkAddNewTask.isChecked()) {
            flags |= Intent.FLAG_ACTIVITY_NEW_TASK;
        }
        if (chkAddClearTask.isChecked()) {
            flags |= Intent.FLAG_ACTIVITY_CLEAR_TASK;
        }
        if (chkAddClearTop.isChecked()) {
            flags |= Intent.FLAG_ACTIVITY_CLEAR_TOP;
        }
        if (chkAddSingleTop.isChecked()) {
            flags |= Intent.FLAG_ACTIVITY_SINGLE_TOP;
        }

        switch (v.getId()){
            case R.id.btnToB:
                i = new Intent(AActivity.this, BActivity.class);
                if (flags != 0){
                    i.setFlags(flags);
                }
                startActivity(i);
                break;
            case R.id.btnToC:
                i = new Intent(AActivity.this, CActivity.class);
                if (flags != 0){
                    i.setFlags(flags);
                }
                startActivity(i);
                break;
            case R.id.btnToD:
                i = new Intent(AActivity.this, DActivity.class);
                if (flags != 0){
                    i.setFlags(flags);
                }
                startActivity(i);
                break;
        }
    }
}
