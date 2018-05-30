package com.example.lap10255.examplerealm;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private UserAdapter adapter;
    private ListView lvUser;
    private Button btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        RealmResults<User> users = realm.where(User.class).findAll();

        adapter = new UserAdapter(this, users);
        lvUser = findViewById(R.id.lvUser);
        lvUser.setAdapter(adapter);

        btnAddUser = findViewById(R.id.btnAddUser);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                LinearLayout layout = new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(10, 10, 10, 10);
                TextView txtName = new TextView(v.getContext());
                txtName.setText(R.string.name);
                EditText edtName = new EditText(v.getContext());
                TextView txtSex = new TextView(v.getContext());
                txtSex.setText(R.string.sex);
                EditText edtSex = new EditText(v.getContext());
                layout.addView(txtName);
                layout.addView(edtName);
                layout.addView(txtSex);
                layout.addView(edtSex);

                builder.setTitle("Add user")
                        .setView(layout);
                builder.setPositiveButton("Add", ((dialog, which) -> {
                    User user = new User(edtName.getText().toString(), edtSex.getText().toString());
                    addUser(user);
                }));
                builder.setNegativeButton("Cancel", ((dialog, which) -> dialog.cancel()));

                builder.show();
            }
        });

    }

    private void addUser(User user) {
        realm.beginTransaction();
        user.setId(UUID.randomUUID().toString());
        User newUser = realm.copyToRealm(user);
        realm.commitTransaction();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
