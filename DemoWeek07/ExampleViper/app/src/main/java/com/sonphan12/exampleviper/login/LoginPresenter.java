package com.sonphan12.exampleviper.login;

import android.app.Activity;
import android.widget.Toast;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.InteractorOutput {
    LoginContract.View view;
    LoginContract.Interactor interactor;
    LoginContract.Router router;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.interactor = new LoginInteractor(this);
        this.router = new LoginRouter((Activity)view);
    }

    @Override
    public void onLoginClicked(String username, String password) {
        interactor.login(username, password);
    }

    @Override
    public void onLoginSuccess() {
        view.toastMessage("Login success!", Toast.LENGTH_SHORT);
        router.goToProfile(null);
    }

    @Override
    public void onLoginFail() {
        view.toastMessage("Login fail!", Toast.LENGTH_SHORT);
    }
}
