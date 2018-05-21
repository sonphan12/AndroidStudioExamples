package com.example.lap10255.examplerxjavadownload;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String URL_STRING1 = "http://file-examples.com/wp-content/uploads/2017/04/file_example_MP4_1920_18MG.mp4";
    public static final String URL_STRING2 = "http://file-examples.com/wp-content/uploads/2017/04/file_example_MP4_1280_10MG.mp4";
    public static final String URL_STRING3 = "https://d2qguwbxlx1sbt.cloudfront.net/TextInMotion-Sample-720p.mp4";

    ImageView imgDownload1, imgDownload2, imgDownload3;
    ProgressBar progress1, progress2, progress3;
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    private void addControls() {
        imgDownload1 = findViewById(R.id.imgDownload1);
        imgDownload2 = findViewById(R.id.imgDownload2);
        imgDownload3 = findViewById(R.id.imgDownload3);
        progress1 = findViewById(R.id.progress1);
        progress2 = findViewById(R.id.progress2);
        progress3 = findViewById(R.id.progress3);

        imgDownload1.setOnClickListener(v -> {
            progress1.setMax(100);
            String fileString = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/file1.mp4";
            disposable.add(DownloadObservable.downloadFile(URL_STRING1, fileString)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DownloadConsumer(progress1)));

        });

        imgDownload2.setOnClickListener(v -> {
            progress2.setMax(100);
            String fileString = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/file2.mp4";
            disposable.add(DownloadObservable.downloadFile(URL_STRING2, fileString)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DownloadConsumer(progress2)));

        });

        imgDownload3.setOnClickListener(v -> {
            progress3.setMax(100);
            String fileString = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/file3.mp4";
            disposable.add(DownloadObservable.downloadFile(URL_STRING3, fileString)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DownloadConsumer(progress3)));

        });

    }
}
