package com.sonphan12.exampleviper.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sonphan12.exampleviper.profile.ProfileActivity;

public class LoginRouter implements LoginContract.Router {
    private Activity activity;

    LoginRouter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void goToProfile(@Nullable Bundle bundle) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        if (bundle != null) intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}
