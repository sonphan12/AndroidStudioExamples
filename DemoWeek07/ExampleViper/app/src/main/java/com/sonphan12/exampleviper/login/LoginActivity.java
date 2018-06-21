package com.sonphan12.exampleviper.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sonphan12.exampleviper.R;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener {
    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    LoginContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);

        bindViews();

        btnLogin.setOnClickListener(this);

    }

    private void bindViews() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    public void toastMessage(String message, int length) {
        Toast.makeText(this, message, length).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                onLoginClicked();
                break;
        }
    }

    private void onLoginClicked() {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        presenter.onLoginClicked(username, password);
    }
}
