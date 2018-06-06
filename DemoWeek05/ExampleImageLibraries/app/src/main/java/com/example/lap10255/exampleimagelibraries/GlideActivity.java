package com.example.lap10255.exampleimagelibraries;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideActivity extends AppCompatActivity {
    ImageView img1;
    ImageView img2;
    ImageView img3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);

        Uri uriImg1 = Uri.parse(PhotoUtil.IMG_URL1);
        Uri uriImg2 = Uri.parse(PhotoUtil.IMG_URL2);
        Uri uriImg3 = Uri.parse(PhotoUtil.IMG_URL3);
        RequestOptions options = new RequestOptions();
        Glide.with(this).load(uriImg1).apply(options.centerCrop()).into(img1);
        Glide.with(this).load(uriImg2).apply(options.centerCrop()).into(img2);
        Glide.with(this).load(uriImg3).apply(options.centerCrop()).into(img3);
    }
}
