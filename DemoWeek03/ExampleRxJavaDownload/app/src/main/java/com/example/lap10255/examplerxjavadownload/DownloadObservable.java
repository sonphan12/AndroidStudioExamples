package com.example.lap10255.examplerxjavadownload;

import android.support.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class DownloadObservable {
    public static Observable<Integer> downloadFile(@NonNull final String urlString, @NonNull final String fileString) {
        return Observable.create(emitter -> {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            float fileSize = conn.getContentLength();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            FileOutputStream fos = new FileOutputStream(fileString);
            byte[] buffer = new byte[1024];
            int count;
            float downloadFileSize = 0;
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                downloadFileSize += count;
                fos.write(buffer, 0, count);
                float percent = downloadFileSize / fileSize * 100;
                emitter.onNext((int)percent);
            }
            fos.close();
            bis.close();
        });
    }
}
