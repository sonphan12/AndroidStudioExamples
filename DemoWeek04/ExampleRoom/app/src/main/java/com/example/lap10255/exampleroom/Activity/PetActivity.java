package com.example.lap10255.exampleroom.Activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lap10255.exampleroom.Model.Pet;
import com.example.lap10255.exampleroom.Database.MyDb;
import com.example.lap10255.exampleroom.Adapter.PetAdapter;
import com.example.lap10255.exampleroom.R;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PetActivity extends AppCompatActivity {
    RecyclerView lvPet;
    PetAdapter adapter;
    Button btnAddPet;

    private static final int DELETE = -123;
    private static final int INSERT = -132;
    private static final int GET_ALL = 12343;

    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        userId = getIntent().getIntExtra("userId", -1);

        addControls();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        MyDb.getDbInstance(this).petDAO().getAll(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listPet -> {
                    adapter.clear();
                    adapter.setListPet((ArrayList<Pet>) listPet);
                });
    }

    private void addControls() {
        lvPet = findViewById(R.id.lvPet);
        adapter = new PetAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lvPet.setLayoutManager(layoutManager);
        lvPet.setAdapter(adapter);

        btnAddPet = findViewById(R.id.btnAddPet);
        btnAddPet.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setPadding(10, 10, 10, 10);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            TextView txtPetName = new TextView(this);
            txtPetName.setText(R.string.pet_name);
            EditText edtPetName = new EditText(this);
            TextView txtSpecies = new TextView(this);
            txtSpecies.setText(R.string.speccies);
            EditText edtSpecies = new EditText(this);

            linearLayout.addView(txtPetName);
            linearLayout.addView(edtPetName);
            linearLayout.addView(txtSpecies);
            linearLayout.addView(edtSpecies);

            builder.setTitle("Add pet");
            builder.setView(linearLayout);

            builder.setPositiveButton("Add", (dialog, which) -> {
                if (userId != -1) {
                    Pet pet = new Pet(edtPetName.getText().toString(),
                            edtSpecies.getText().toString(), userId);
                    addPet(pet);
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();

        });
    }

    @SuppressLint("CheckResult")
    private void addPet(Pet pet) {
        Single.create(emitter -> {
            MyDb.getDbInstance(this).petDAO().insert(pet);
            emitter.onSuccess(pet);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> updateUI((Pet)p, INSERT), this::handleError);
    }

    private void handleError(Throwable e) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    private void updateUI(Pet pet, int option) {
        if (option == DELETE) {

        } else if (option == INSERT) {
            adapter.add(pet);
            Toast.makeText(this, "Added!", Toast.LENGTH_SHORT).show();
        } else if (option == GET_ALL) {
            adapter.add(pet);
            Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
        }
    }
}
