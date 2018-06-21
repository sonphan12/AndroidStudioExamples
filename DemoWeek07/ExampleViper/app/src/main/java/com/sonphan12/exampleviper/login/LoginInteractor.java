package com.sonphan12.exampleviper.login;

public class LoginInteractor implements LoginContract.Interactor {
    LoginContract.InteractorOutput output;

    public LoginInteractor(LoginContract.InteractorOutput output) {
        this.output = output;
    }

    @Override
    public void login(String username, String password) {
        if (username.equals("son") && password.equals("12345")) {
            output.onLoginSuccess();
        } else {
            output.onLoginFail();
        }
    }
}
