package com.example.lap10255.examplebroadcastreceiver;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    WifiBroadcastReceiver receiver;
    IntentFilter filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiver = new WifiBroadcastReceiver();
        filter = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
        registerReceiver(receiver, filter);
    }
}
