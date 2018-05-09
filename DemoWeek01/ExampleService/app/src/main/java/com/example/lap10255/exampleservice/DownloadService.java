package com.example.lap10255.exampleservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.webkit.URLUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends Service {
    private String mURL;
    private NotificationManager notificationManager;
    private Notification.Builder builder;

    public DownloadService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        builder = new Notification.Builder(getApplicationContext());
    }

    private void download() {
        builder.setContentTitle("Downloading").setContentText("Downloading")
                .setSmallIcon(R.drawable.ic_launcher_foreground).setContentInfo("0%");
        int count;
        try{
            URL url = new URL(mURL);
            URLConnection connection = url.openConnection();
            connection.connect();

            int fileLength = connection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream());
            String fileName = URLUtil.guessFileName(mURL, null, null);
            File download = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            OutputStream output = new FileOutputStream(download);
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1){
                total += count;
                progressChange((int)(total * 100/fileLength));
                output.write(data, 0, count);

            }
            output.flush();
            output.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mURL = intent.getStringExtra("url");
        new Thread(new Runnable() {
            @Override
            public void run() {
                download();
            }
        }).start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void progressChange(int progress){
        if (progress < 100){
            //builder.setProgress(100, progress, true);
            builder.setContentText(String.valueOf(progress) + "%");
            notificationManager.notify(12, builder.build());

        } else {
            builder.setContentText("Download complete!")
                    .setOngoing(false);
            notificationManager.notify(12, builder.build());
        }
    }
}
