package com.sonphan12.exampleclean.presentation;

import android.content.Context;
import android.widget.Toast;

import com.sonphan12.exampleclean.UseCase;
import com.sonphan12.exampleclean.data.ContactRepository;
import com.sonphan12.exampleclean.domain.Contact;
import com.sonphan12.exampleclean.domain.UsecaseAllContact;
import com.sonphan12.exampleclean.domain.UsecaseSearch;
import com.sonphan12.exampleclean.utils.ApplySchedulers;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class ContactPresenter implements ContactContract.Presenter {
    private CompositeDisposable disposable = new CompositeDisposable();
    private ContactContract.View view;
    private UseCase<Void, Observable<List<Contact>>>  useCaseAllContact;
    private UseCase<String, Observable<List<Contact>>> useCaseSearch;

    ContactPresenter(ContactContract.View view, UsecaseAllContact useCaseAllContact, UsecaseSearch useCaseSearch) {
        this.view = view;
        this.useCaseAllContact = useCaseAllContact;
        this.useCaseSearch = useCaseSearch;
    }

    @Override
    public void getAllContact(Context ctx) {
        disposable.clear();
        disposable.add(
                useCaseAllContact
                .executeUsecase(ctx, null)
                .compose(ApplySchedulers.applyScheduler())
                .subscribe(listContact -> {
                    view.hideProgressBar();
                    view.showContacts(listContact);
                }
                , e -> view.toastMessage(e.toString(), Toast.LENGTH_SHORT))
        );
    }

    @Override
    public void getSearchContact(Context ctx, String query) {
        disposable.clear();
        disposable.add(
                useCaseSearch
                .executeUsecase(ctx, query)
                .compose(ApplySchedulers.applyScheduler())
                .subscribe(listContact -> {
                    view.hideProgressBar();
                    view.showContacts(listContact);
                }
                , e -> view.toastMessage(e.toString(), Toast.LENGTH_SHORT))
        );
    }

    @Override
    public void bindPresenter() {
        view.setPresenter(this);
    }

    @Override
    public void unbindPresenter() {
        view.setPresenter(null);
        disposable.dispose();
    }
}
