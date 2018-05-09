package com.example.lap10255.intentflagexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BActivity extends AppCompatActivity{
    @BindView(R.id.btnToA) Button btnToA;
    @BindView(R.id.btnToB) Button btnToB;
    @BindView(R.id.btnToC)Button btnToC;
    @BindView(R.id.btnToD)Button btnToD;

    @BindView(R.id.chkAddNewTask) CheckBox chkAddNewTask;
    @BindView(R.id.chkAddClearTask) CheckBox chkAddClearTask;
    @BindView(R.id.chkAddClearTop) CheckBox chkAddClearTop;
    @BindView(R.id.chkAddSingleTop) CheckBox chkAddSingleTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_b);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnToA, R.id.btnToB, R.id.btnToC, R.id.btnToD})
    public void buttonClicked(View v){
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
            case R.id.btnToA:
                i = new Intent(BActivity.this, AActivity.class);
                if (flags != 0){
                    i.setFlags(flags);
                }
                startActivity(i);
                break;
            case R.id.btnToB:
                i = new Intent(BActivity.this, BActivity.class);
                if (flags != 0){
                    i.setFlags(flags);
                }
                startActivity(i);
                break;
            case R.id.btnToC:
                i = new Intent(BActivity.this, CActivity.class);
                if (flags != 0){
                    i.setFlags(flags);
                }
                startActivity(i);
                break;
            case R.id.btnToD:
                i = new Intent(BActivity.this, DActivity.class);
                if (flags != 0){
                    i.setFlags(flags);
                }
                startActivity(i);
                break;
        }
    }
}
