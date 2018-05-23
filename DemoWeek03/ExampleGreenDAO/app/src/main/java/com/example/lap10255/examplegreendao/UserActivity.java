package com.example.lap10255.examplegreendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.lap10255.examplegreendao.Model.DaoSession;
import com.example.lap10255.examplegreendao.Model.User;
import com.example.lap10255.examplegreendao.Model.UserDao;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UserActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtAge;
    private Button btnAdd;
    private Button btnGet;
    private Button btnClear;
    private RecyclerView lvUser;
    private SearchView searchView;

    private UserDao userDao;
    private Query<User> userQuery;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addViews();

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        userDao = daoSession.getUserDao();


//        showAllUsers();
    }

    private void showAllUsers() {
        userQuery = userDao.queryBuilder().orderAsc(UserDao.Properties.Name).build();
        ArrayList<User> listUser = (ArrayList<User>) userQuery.list();
        adapter.setList(listUser);
    }

    private void showUserFromList(ArrayList<User> list) {
        adapter.setList(list);
    }

    private void addViews() {
        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setEnabled(false);
        btnGet = findViewById(R.id. btnGet);
        btnClear = findViewById(R.id.btnClear);
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchObservable.search(newText)
                                .filter(query -> !query.isEmpty())
                                .debounce(300, TimeUnit.MILLISECONDS)
                                .switchMap((Function<String, ObservableSource<ArrayList<User>>>) s -> {
                                    ArrayList<User> listUser = (ArrayList<User>) userDao.queryBuilder()
                                            .where(UserDao.Properties.Name
                                                    .like("%" + newText + "%"))
                                            .orderAsc(UserDao.Properties.Name).list();
                                    return io.reactivex.Observable.just(listUser);
                                })
                                .distinctUntilChanged()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(getConsumer());
                return true;
            }
        });

        TextWatcher textWatcher = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtName.getText().toString().isEmpty() && !edtAge.getText().toString().isEmpty()){
                    btnAdd.setEnabled(true);
                } else {
                    btnAdd.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        edtName.addTextChangedListener(textWatcher);
        edtAge.addTextChangedListener(textWatcher);

        lvUser = findViewById(R.id.lvUser);

        lvUser.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this, new ArrayList<>());
        lvUser.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            User user = new User(edtName.getText().toString(), Integer.parseInt(edtAge.getText().toString()));
            userDao.insert(user);
            ArrayList<User> listUser = adapter.getList();
            listUser.add(user);
            showUserFromList(listUser);
        });

        btnGet.setOnClickListener(v -> showAllUsers());

        btnClear.setOnClickListener(v -> showUserFromList(new ArrayList<User>()));
    }

    private Consumer<ArrayList<User>> getConsumer() {
        return this::showUserFromList;
    }
}
