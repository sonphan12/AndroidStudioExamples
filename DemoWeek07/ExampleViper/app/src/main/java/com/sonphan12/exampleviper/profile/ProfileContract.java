package com.sonphan12.exampleviper.profile;

import com.sonphan12.exampleviper.entity.User;

public interface ProfileContract {
    interface View {
        void showProfile(User user);
        void toastMessage(String message, int length);
    }

    interface Presenter {
        void getProfile();
    }

    interface Interactor {
        void getProfile();
    }

    interface InteractorOutput {
        void onGetProfileSuccess(User user);
        void onGetProfileFailure();
    }
}
