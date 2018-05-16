package com.example.lap10255.exampleasynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;

public class DownloadTask extends AsyncTask<Void, Integer, Void> {
    private WeakReference<Activity> mActivity;
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        ProgressBar progressDownload = mActivity.get().findViewById(R.id.progressDownload);
        Button btnDownload = mActivity.get().findViewById(R.id.btnDownload);
        progressDownload.setProgress(values[0]);
        if (values[0] == 100) {
            Toast.makeText(mActivity.get(), "Download completed!", Toast.LENGTH_LONG).show();
            btnDownload.setEnabled(true);
        }
    }

    DownloadTask(Activity mActivity) {
        super();
        this.mActivity = new WeakReference<>(mActivity);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Pseudo download
        int totalPercent = 100;
        int currentPercent = 0;
        Random random = new Random();
        while (currentPercent < totalPercent) {
            try {
                Thread.sleep(1000);
                currentPercent += random.nextInt(5) + 1;
                if (currentPercent > totalPercent) {
                    currentPercent = totalPercent;
                }
                publishProgress(currentPercent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
