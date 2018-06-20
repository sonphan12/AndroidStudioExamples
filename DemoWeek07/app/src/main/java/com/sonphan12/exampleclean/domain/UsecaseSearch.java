package com.sonphan12.exampleclean.domain;

import android.content.Context;

import com.sonphan12.exampleclean.UseCase;
import com.sonphan12.exampleclean.data.ContactRepository;

import java.util.List;

import io.reactivex.Observable;

public class UsecaseSearch implements UseCase<String, Observable<List<Contact>>> {
    private ContactRepository contactRepository;


    public UsecaseSearch() {
        this.contactRepository = ContactRepository.getInstance();
    }

    @Override
    public Observable<List<Contact>> executeUsecase(Context ctx, String param) {
        return contactRepository
                .getSearchContact(ctx, param)
                .flatMapIterable(item -> item)
                .concatMap(item -> Observable.just(new Contact(item.getName(), item.isHasPhoneNumber(), item.getPhoneNumber())))
                .toList()
                .toObservable();
    }
}
