package com.example.lap10255.sqliteexample;

import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.edtName)EditText edtName;
    @BindView(R.id.edtAge)EditText edtAge;
    @BindView(R.id.btnAdd)Button btnAdd;
    @BindView(R.id.lvInfo)ListView lvInfo;
    private InfoListViewAdapter lvAdapter;
    private ArrayList<Info> listInfo;
    private MyDBHelper myDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        listInfo = new ArrayList<>();
        lvAdapter = new InfoListViewAdapter(this, R.layout.item, listInfo);
        lvInfo.setAdapter(lvAdapter);
        myDBHelper = new MyDBHelper(this);
    }

    @OnClick(R.id.btnAdd)
    public void performClick(View v) {
        if (edtName.getText().toString().isEmpty() || edtAge.getText().toString().isEmpty()) {
            return;
        }
        Info i = new Info(edtName.getText().toString(), Integer.parseInt(edtAge.getText().toString()));
        long id = myDBHelper.insertInfo(i);
        if (id != -1) {
            i.setId(id);
            lvAdapter.add(i);
            lvAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor res = myDBHelper.getAllData();
        while (res.moveToNext()) {
            Info i =  new Info(res.getLong(res.getColumnIndex("id")), res.getString(res.getColumnIndex("name")), res.getInt(res.getColumnIndex("age")));
            listInfo.add(i);
            lvAdapter.add(i);
            lvAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
