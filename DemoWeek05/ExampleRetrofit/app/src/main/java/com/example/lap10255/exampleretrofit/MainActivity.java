package com.example.lap10255.exampleretrofit;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lap10255.exampleretrofit.Data.JobService;
import com.example.lap10255.exampleretrofit.Model.Job;
import com.example.lap10255.exampleretrofit.Util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvJob;
    JobAdapter adapter;
    Button btnGetAll;
    Button btnGetFromInfo;
    JobService jobService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobService = Util.getJobService();

        addControls();
    }

    private void addControls() {
        rvJob = findViewById(R.id.rvJob);
        adapter = new JobAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvJob.setLayoutManager(layoutManager);
        rvJob.setAdapter(adapter);

        btnGetAll = findViewById(R.id.btnGetAll);
        btnGetFromInfo = findViewById(R.id.btnGetFromInfo);

        btnGetAll.setOnClickListener(v -> loadJobs());
        btnGetFromInfo.setOnClickListener(v -> loadJobsFromInfo());
    }

    private void loadJobs() {
        jobService.getAllJobs().enqueue(new Callback<ArrayList<Job>>() {
            @Override
            public void onResponse(Call<ArrayList<Job>> call, Response<ArrayList<Job>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Job> listJobs = response.body();
                    adapter.setListJobs(listJobs);
                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Job>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJobsFromInfo() {
        inputFromDialog();
    }

    private void inputFromDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(10, 10, 10, 10);

        EditText edtDescription = new EditText(this);
        edtDescription.setHint("Enter job description");
        EditText edtLocation = new EditText(this);
        edtLocation.setHint("Enter job location");
        LinearLayout childLayout = new LinearLayout(this);
        childLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView txtAskOnlyFulltime = new TextView(this);
        txtAskOnlyFulltime.setText(R.string.only_fulltime);
        CheckBox chkOnlyFulltime = new CheckBox(this);


        layout.addView(edtDescription);
        layout.addView(edtLocation);
        childLayout.addView(txtAskOnlyFulltime);
        childLayout.addView(chkOnlyFulltime);
        layout.addView(childLayout);

        builder.setView(layout);

        String queryDescription = edtDescription.getText().toString().isEmpty() ? null : edtDescription.getText().toString();
        String queryLocation = edtLocation.getText().toString().isEmpty() ? null : edtLocation.getText().toString();
        String queryType = chkOnlyFulltime.isChecked() ? "true" : null;
        builder.setPositiveButton("Query", (dialog, which) -> queryJobs(
                queryDescription,
                queryLocation,
                queryType
        ));
        builder.setNegativeButton("Cancel", ((dialog, which) -> dialog.cancel()));

        builder.show();

    }

    private void queryJobs(String description, String location, String type) {
        jobService.getJobFromInfo(description, location, type).enqueue(new Callback<ArrayList<Job>>() {
            @Override
            public void onResponse(Call<ArrayList<Job>> call, Response<ArrayList<Job>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Job> listJobs = response.body();
                    adapter.setListJobs(listJobs);
                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Job>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
