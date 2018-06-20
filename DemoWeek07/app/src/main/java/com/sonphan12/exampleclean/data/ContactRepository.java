package com.sonphan12.exampleclean.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.sonphan12.exampleclean.domain.Contact;
import com.sonphan12.exampleclean.utils.ApplySchedulers;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class ContactRepository {
    private static ContactRepository INSTANCE = null;
    public static ContactRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ContactRepository();
        }
        return INSTANCE;
    }

    private ContactRepository() {
    }

    public Observable<List<Contact>> getAllContact(Context ctx) {
        ArrayList<Contact> listContact = new ArrayList<>();
        ContentResolver cr = ctx.getContentResolver();
        Cursor cur = ctx.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null,
                null, null);
        return Observable.create(emitter -> {
            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));
                    String phoneNumber = "";
                    boolean hasPhoneNumber = false;
                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        hasPhoneNumber = true;
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur != null && pCur.moveToNext()) {
                            phoneNumber = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        if (pCur != null) {
                            pCur.close();
                        }
                    }
                    listContact.add(new Contact(name, hasPhoneNumber, phoneNumber));
                }
            }
            if (cur != null) {
                cur.close();
            }
            emitter.onNext(listContact);
            emitter.onComplete();
        });
    }

    public Observable<List<Contact>> getSearchContact(Context ctx, String query) {
        ArrayList<Contact> listContact = new ArrayList<>();
        ContentResolver cr = ctx.getContentResolver();
        String selection = ContactsContract.Contacts.DISPLAY_NAME + " LIKE \'%" + query + "%\'";
        Cursor cur = ctx.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, selection,
                null, null);
        return Observable.create(emitter -> {
            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));
                    String phoneNumber = "";
                    boolean hasPhoneNumber = false;
                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        hasPhoneNumber = true;
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur != null && pCur.moveToNext()) {
                            phoneNumber = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        if (pCur != null) {
                            pCur.close();
                        }
                    }
                    listContact.add(new Contact(name, hasPhoneNumber, phoneNumber));
                }
            }
            if (cur != null) {
                cur.close();
            }
            emitter.onNext(listContact);
            emitter.onComplete();
        });
    }
}
