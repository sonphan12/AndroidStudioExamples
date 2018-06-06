package com.example.lap10255.exampleimagelibraries;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;

public class FrescoActivity extends AppCompatActivity {
    SimpleDraweeView img1;
    SimpleDraweeView img2;
    SimpleDraweeView img3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);

        Uri uriImg1 = Uri.parse(PhotoUtil.IMG_URL1);
        Uri uriImg2 = Uri.parse(PhotoUtil.IMG_URL2);
        Uri uriImg3 = Uri.parse(PhotoUtil.IMG_URL3);

        img1.setImageURI(uriImg1);
        img2.setImageURI(uriImg2);
        img3.setImageURI(uriImg3);
    }
}
