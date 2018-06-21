package com.sonphan12.exampleviper.profile;

import android.widget.Toast;

import com.sonphan12.exampleviper.entity.User;

public class ProfilePresenter implements ProfileContract.Presenter, ProfileContract.InteractorOutput {
    ProfileContract.View view;
    ProfileContract.Interactor interactor;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
        this.interactor = new ProfileInteractor(this);
    }

    @Override
    public void getProfile() {
        interactor.getProfile();
    }

    @Override
    public void onGetProfileSuccess(User user) {
        view.showProfile(user);
    }

    @Override
    public void onGetProfileFailure() {
        view.toastMessage("Get profile failed!", Toast.LENGTH_SHORT);
        User user = new User("Unknown", "Unknown");
        view.showProfile(user);
    }
}
