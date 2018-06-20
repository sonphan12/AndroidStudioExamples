package com.sonphan12.exampleclean.presentation;

import android.content.Context;

import com.sonphan12.exampleclean.BasePresenter;
import com.sonphan12.exampleclean.BaseView;
import com.sonphan12.exampleclean.domain.Contact;

import java.util.List;

public interface ContactContract {
    interface View extends BaseView<Presenter> {
        void showContacts(List<Contact> listContact);
        void showProgressBar();
        void hideProgressBar();
        void toastMessage(String message, int length);
        void onSearch(String query);
    }

    interface Presenter extends BasePresenter {
        void getAllContact(Context ctx);
        void getSearchContact(Context ctx, String query);
    }
}
