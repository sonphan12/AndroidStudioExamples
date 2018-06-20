package com.sonphan12.exampleclean.presentation;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonphan12.exampleclean.R;
import com.sonphan12.exampleclean.domain.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    Context ctx;
    List<Contact> listContact;

    public ContactAdapter(Context ctx) {
        this.ctx =ctx;
        this.listContact = new ArrayList<>();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_contact, parent, false);
        ContactViewHolder holder = new ContactViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = listContact.get(position);
        holder.txtName.setText(contact.getName());

        holder.imgAvatar.setImageResource(R.drawable.default_avatar);

        if (contact.isHasPhoneNumber()) {
            holder.txtPhone.setText(contact.getPhoneNumber());
        } else {
            holder.txtPhone.setText("Unknown");
        }
    }

    @Override
    public int getItemCount() {
        return listContact.size();
    }

    public void setListContact(List<Contact> listContact) {
        this.listContact = listContact;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAvatar) ImageView imgAvatar;
        @BindView(R.id.txtName) TextView txtName;
        @BindView(R.id.txtPhone) TextView txtPhone;
        ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
