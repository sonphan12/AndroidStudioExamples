package com.sonphan12.exampleviper.profile;

import com.sonphan12.exampleviper.entity.User;
import com.sonphan12.exampleviper.entity.UserRepository;

public class ProfileInteractor implements ProfileContract.Interactor {
    ProfileContract.InteractorOutput output;

    public ProfileInteractor(ProfileContract.InteractorOutput output) {
        this.output = output;
    }

    @Override
    public void getProfile() {
        User user = UserRepository.getCurrentUser();
        if (user != null) {
            output.onGetProfileSuccess(user);
        } else {
            output.onGetProfileFailure();
        }
    }
}
