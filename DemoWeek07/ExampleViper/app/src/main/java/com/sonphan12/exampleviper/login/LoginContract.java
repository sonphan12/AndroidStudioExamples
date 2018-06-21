package com.sonphan12.exampleviper.login;

import android.os.Bundle;

public interface LoginContract {
    interface View {
        void toastMessage(String message, int length);
    }

    interface Presenter {
        void onLoginClicked(String username, String password);
    }

    interface Interactor {
        void login(String username, String password);
    }

    interface InteractorOutput {
        void onLoginSuccess();
        void onLoginFail();
    }

    interface Router {
        void goToProfile(Bundle bundle);
    }
}
