package com.example.lap10255.examplesaverestorestatefragment;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private BlankFragment mSaveFragment;
    private Button btnShow;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSaveFragment != null) {
            getSupportFragmentManager().putFragment(outState, "saveFragment", mSaveFragment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mSaveFragment = (BlankFragment) getSupportFragmentManager().getFragment(savedInstanceState, "saveFragment");
        } else {
            mSaveFragment = null;
        }

        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSaveFragment == null) {
                    mSaveFragment = new BlankFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mSaveFragment).commit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSaveFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mSaveFragment).commit();
        }
    }
}
