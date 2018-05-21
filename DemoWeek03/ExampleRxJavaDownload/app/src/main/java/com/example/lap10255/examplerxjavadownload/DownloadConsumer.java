package com.example.lap10255.examplerxjavadownload;

import android.widget.ProgressBar;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;


public class DownloadConsumer implements io.reactivex.functions.Consumer<Integer> {

    private WeakReference<ProgressBar> progress;

    public DownloadConsumer(ProgressBar progress) {
        this.progress = new WeakReference<>(progress);
    }


    @Override
    public void accept(Integer percent) {
        if (progress.get() != null) {
            progress.get().setProgress(percent);
        }
    }
}
