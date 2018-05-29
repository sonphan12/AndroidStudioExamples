package com.example.lap10255.exampleroom.Activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lap10255.exampleroom.Model.User;
import com.example.lap10255.exampleroom.Database.MyDb;
import com.example.lap10255.exampleroom.R;
import com.example.lap10255.exampleroom.Adapter.UserAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView lvUser;
    UserAdapter userAdapter;
    android.widget.SearchView searchUser;
    Button btnGetAllUser;
    Button btnAddUser;
    MyDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
    }

    private void addControls() {
        lvUser = findViewById(R.id.lvUser);
        userAdapter = new UserAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        lvUser.setLayoutManager(manager);
        lvUser.setAdapter(userAdapter);

        db = MyDb.getDbInstance(this);

        searchUser = findViewById(R.id.searchUser);

        btnGetAllUser = findViewById(R.id.btnGetAllUser);
        btnAddUser = findViewById(R.id.btnAddUser);
        btnGetAllUser.setOnClickListener(this);
        btnAddUser.setOnClickListener(this);

        searchUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @SuppressLint("CheckResult")
            @Override
            public boolean onQueryTextChange(String newText) {
                db.userDAO().findByName(newText)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(listUser -> {
                            userAdapter.clear();
                            userAdapter.setListUser((ArrayList<User>) listUser);
                        }, e -> {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        });
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddUser:
                addUser();
                break;
            case R.id.btnGetAllUser:
                getAllUser();
                break;
        }
    }

    private void addUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        TextView txtName = new TextView(this);
        txtName.setText(R.string.user_name);
        final EditText inputName = new EditText(this);
        TextView txtSex = new TextView(this);
        txtSex.setText(R.string.sex);
        final EditText inputSex = new EditText(this);
        TextView txtAge = new TextView(this);
        txtAge.setText(R.string.age);
        final EditText inputAge = new EditText(this);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(10, 10, 10, 10);
        layout.addView(txtName);
        layout.addView(inputName);
        layout.addView(txtSex);
        layout.addView(inputSex);
        layout.addView(txtAge);
        layout.addView(inputAge);

        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = inputName.getText().toString();
            String sex = inputSex.getText().toString();
            int age = Integer.parseInt(inputAge.getText().toString());
            
            User user = new User(name, sex, age);
            if (userAdapter != null) {
                userAdapter.add(user);
                addUserToDB(user);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @SuppressLint("CheckResult")
    private void getAllUser(){
        db.userDAO().getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(listUser -> userAdapter.setListUser((ArrayList<User>) listUser),
                        error -> { Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                                   error.printStackTrace();
                                 });
    }


    @SuppressLint("CheckResult")
    private void addUserToDB(User user) {
        Single.create(emitter -> {
            db.userDAO().insert(user);
            emitter.onSuccess(user);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(u -> Toast.makeText(this, "Added!", Toast.LENGTH_SHORT).show(),
                        e -> {
                            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        });
    }
}
