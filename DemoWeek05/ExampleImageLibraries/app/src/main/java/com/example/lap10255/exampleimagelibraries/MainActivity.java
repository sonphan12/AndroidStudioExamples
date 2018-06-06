package com.example.lap10255.exampleimagelibraries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnFresco;
    Button btnGlide;
    Button btnPicasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addListeners();
    }

    private void addControls() {
        btnFresco = findViewById(R.id.btnFresco);
        btnGlide = findViewById(R.id.btnGlide);
        btnPicasso = findViewById(R.id.btnPicasso);
    }

    private void addListeners() {
        btnFresco.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, FrescoActivity.class);
            startActivity(i);
        });

        btnGlide.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, GlideActivity.class);
            startActivity(i);
        });

        btnPicasso.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, PicassoActivity.class);
            startActivity(i);
        });
    }
}
