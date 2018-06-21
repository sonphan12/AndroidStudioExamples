package com.sonphan12.exampleviper.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.sonphan12.exampleviper.R;
import com.sonphan12.exampleviper.entity.User;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View {
    TextView txtUsername;
    TextView txtAge;
    ProfileContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bindViews();
        presenter = new ProfilePresenter(this);
        presenter.getProfile();
    }

    private void bindViews() {
        txtUsername = findViewById(R.id.txtUsername);
        txtAge = findViewById(R.id.txtAge);
    }

    @Override
    public void showProfile(User user) {
        txtUsername.append(user.getName());
        txtAge.append(user.getAge());
    }

    @Override
    public void toastMessage(String message, int length) {
        Toast.makeText(this, message, length).show();
    }
}
