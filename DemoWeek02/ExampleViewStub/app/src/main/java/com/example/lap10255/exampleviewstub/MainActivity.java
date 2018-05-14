package com.example.lap10255.exampleviewstub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnShow;
    private ViewStub viewStub;
    private boolean isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isShow = false;
        viewStub = null;

        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    isShow = false;
                    btnShow.setText("Show Viewstub");
                    if (viewStub != null) {
                        viewStub.setVisibility(View.GONE);
                    }
                } else {
                    isShow = true;
                    btnShow.setText("Invisible Viewstub");
                    if (viewStub == null) {
                        viewStub = findViewById(R.id.viewStub);
                        View inflatedView = viewStub.inflate();
                        ((Button) inflatedView.findViewById(R.id.btn1)).setText("Viewstub");
                    } else {
                        viewStub.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}
