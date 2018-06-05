package com.example.lap10255.exampleretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lap10255.exampleretrofit.Model.Job;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class JobDetailActivity extends AppCompatActivity {
    ImageView imgLogo;
    TextView txtTitle;
    TextView txtType;
    TextView txtLocation;
    TextView txtCompany;
    TextView txtCreatedAt;
    TextView txtDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        addControls();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Job job = (Job) bundle.getSerializable("job");
            updateUI(job);
        }
    }

    private void addControls() {
        imgLogo = findViewById(R.id.imgLogo);
        txtTitle = findViewById(R.id.txtTitle);
        txtType = findViewById(R.id.txtType);
        txtLocation = findViewById(R.id.txtLocation);
        txtCompany = findViewById(R.id.txtCompany);
        txtCreatedAt = findViewById(R.id.txtCreatedAt);
        txtDescription = findViewById(R.id.txtDescription);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void updateUI(Job job) {
        Glide.with(this).load(job.getCompanyLogo()).into(imgLogo);
        txtTitle.setText(job.getTitle());
        txtType.setText(job.getType());
        txtLocation.setText(job.getLocation());
        txtCreatedAt.setText(job.getCreatedAt());

        txtDescription.setText(Html.fromHtml(job.getDescription()));
    }
}
