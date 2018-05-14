package com.example.lap10255.exampleframelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button btnChangeImage;
    private ImageView imgA;
    private ImageView imgB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgA = findViewById(R.id.imgA);
        imgB = findViewById(R.id.imgB);

        btnChangeImage = findViewById(R.id.btnChangeImage);
        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgA.getVisibility() == View.VISIBLE) {
                    imgA.setVisibility(View.INVISIBLE);
                    imgB.setVisibility(View.VISIBLE);
                } else {
                    imgA.setVisibility(View.VISIBLE);
                    imgB.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
