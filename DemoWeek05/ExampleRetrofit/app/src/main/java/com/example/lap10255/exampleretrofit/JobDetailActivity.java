package com.example.lap10255.exampleretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lap10255.exampleretrofit.Components.DaggerJobDetailComponent;
import com.example.lap10255.exampleretrofit.Components.JobDetailComponent;
import com.example.lap10255.exampleretrofit.Model.Job;
import com.example.lap10255.exampleretrofit.Modules.EventBusModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

public class JobDetailActivity extends AppCompatActivity {
    ImageView imgLogo;
    TextView txtTitle;
    TextView txtType;
    TextView txtLocation;
    TextView txtCompany;
    TextView txtCreatedAt;
    TextView txtDescription;
    @Inject
    EventBus eventBus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        inject();
        addControls();

    }

    private void inject() {
        JobDetailComponent jobDetailComponent = DaggerJobDetailComponent.builder()
                .eventBusModule(new EventBusModule())
                .build();
        jobDetailComponent.injectJobDetailActivity(this);
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
        eventBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventBus.unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onJobEvent(Job job) {
        Glide.with(this).load(job.getCompanyLogo()).into(imgLogo);
        txtTitle.setText(job.getTitle());
        txtType.setText(job.getType());
        txtLocation.setText(job.getLocation());
        txtCreatedAt.setText(job.getCreatedAt());

        txtDescription.setText(Html.fromHtml(job.getDescription()));
    }
}
