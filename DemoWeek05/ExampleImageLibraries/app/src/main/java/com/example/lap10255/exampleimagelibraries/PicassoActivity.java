package com.example.lap10255.exampleimagelibraries;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoActivity extends AppCompatActivity {
    ImageView img1;
    ImageView img2;
    ImageView img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);

        Uri uriImg1 = Uri.parse(PhotoUtil.IMG_URL1);
        Uri uriImg2 = Uri.parse(PhotoUtil.IMG_URL2);
        Uri uriImg3 = Uri.parse(PhotoUtil.IMG_URL3);

        Picasso picasso = new Picasso.Builder(this).listener((picasso1, uri, e) -> e.printStackTrace()).build();

        picasso.load(uriImg1).fit().into(img1);
        picasso.load(uriImg2).fit().into(img2);
        picasso.load(uriImg3).fit().into(img3);

    }
}
