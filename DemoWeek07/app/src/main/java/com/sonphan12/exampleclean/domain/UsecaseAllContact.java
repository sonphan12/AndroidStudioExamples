package com.sonphan12.exampleclean.domain;

import android.content.Context;

import com.sonphan12.exampleclean.UseCase;
import com.sonphan12.exampleclean.data.ContactRepository;

import java.util.List;

import io.reactivex.Observable;

public class UsecaseAllContact implements UseCase<Void, Observable<List<Contact>>> {
    private ContactRepository contactRepository;


    public UsecaseAllContact() {
        this.contactRepository = ContactRepository.getInstance();
    }


    @Override
    public Observable<List<Contact>> executeUsecase(Context ctx, Void param) {
        return contactRepository.getAllContact(ctx);
    }
}
