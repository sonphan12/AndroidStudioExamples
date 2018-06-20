package com.sonphan12.exampleclean.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.sonphan12.exampleclean.R;
import com.sonphan12.exampleclean.domain.Contact;
import com.sonphan12.exampleclean.domain.UsecaseAllContact;
import com.sonphan12.exampleclean.domain.UsecaseSearch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ContactContract.View {
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.rvContact) RecyclerView rvContact;
    @BindView(R.id.searchView) SearchView searchView;
    ContactContract.Presenter presenter;
    ContactAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpRecyclerView();

        presenter = new ContactPresenter(this, new UsecaseAllContact(), new UsecaseSearch());
        presenter.bindPresenter();
        presenter.getAllContact(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                onSearch(newText);
                return true;
            }
        });
    }

    private void setUpRecyclerView() {
        adapter = new ContactAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvContact.setLayoutManager(manager);
        rvContact.setAdapter(adapter);
    }

    @Override
    public void showContacts(List<Contact> listContact) {
        adapter.setListContact(listContact);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void toastMessage(String message, int length) {
        Toast.makeText(this, message, length).show();
    }

    @Override
    public void onSearch(String query) {
        presenter.getSearchContact(this, query);
    }


    @Override
    public void setPresenter(ContactContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbindPresenter();
    }
}
