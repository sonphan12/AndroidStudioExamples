package com.example.lap10255.examplerxjava2loaddata;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView lvInfo;
    private Button btnMerge;
    private Button btnFlatmap;
    private Button btnConcatFilter;
    private Button btnZip;
    private Button btnCombineLatest;
    private Button btnClear;
    private PersonAdapter adapter;
    private static final String urlString =
            "https://raw.githubusercontent.com/sonphan12/AndroidStudioExamples/master/DemoWeek03/ExampleRxJava2LoadData/sample_info.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
    }

    private void addControls() {
        lvInfo = findViewById(R.id.lvInfo);
        Button btnConcat = findViewById(R.id.btnConcat);
        btnConcat.setOnClickListener(this);
        btnMerge = findViewById(R.id.btnMerge);
        btnMerge.setOnClickListener(this);
        btnFlatmap = findViewById(R.id.btnFlatMap);
        btnFlatmap.setOnClickListener(this);
        btnConcatFilter = findViewById(R.id.btnConcatFilter);
        btnConcatFilter.setOnClickListener(this);
        btnZip = findViewById(R.id.btnZip);
        btnZip.setOnClickListener(this);
        btnCombineLatest = findViewById(R.id.btnCombineLatest);
        btnCombineLatest.setOnClickListener(this);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        adapter = new PersonAdapter(this, R.layout.person_item, new ArrayList<>());
        lvInfo.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        adapter.clear();
        switch (v.getId()) {
            case R.id.btnConcat:
                doConcat();
                break;
            case R.id.btnMerge:
                doMerge();
                break;
            case R.id.btnConcatFilter:
                doConcatFilter();
                break;
            case R.id.btnZip:
                doZip();
                break;
            case R.id.btnCombineLatest:
                doCombineLatest();
                break;
            case R.id.btnFlatMap:
                doFlatMap();
                break;
            case R.id.btnClear:
                break;
        }

    }



    @SuppressLint("CheckResult")
    void doMerge() {
        Observable<ArrayList<Person>> serverObservable = createServerObservable();
        Observable<ArrayList<Person>> localObservable = createLocalObservable();
        // Merge two emissions
        Observable<ArrayList<Person>> concatObservable =
                Observable.merge(serverObservable, localObservable);

        concatObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listPerson -> adapter.addAll(listPerson));
    }

    @SuppressLint("CheckResult")
    void doConcat() {
        Observable<ArrayList<Person>> serverObservable = createServerObservable();
        Observable<ArrayList<Person>> localObservable = createLocalObservable();
        // Concat two emissions
        Observable<ArrayList<Person>> concatObservable =
                Observable.concat(serverObservable, localObservable);

        concatObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listPerson -> adapter.addAll(listPerson));
    }

    @SuppressLint("CheckResult")
    void doConcatFilter() {
        // FlatMap serverObservable into emissions of Persons
        Observable<Person> serverObservable = createServerObservable()
                .flatMap(Observable::fromIterable);
        // FlatMap localObservable into emissions of Persons
        Observable<Person> localObservable = createLocalObservable()
                .flatMap(Observable::fromIterable);
        // Concat those emissions with filter of age > 10
        Observable<Person> concatFilterObservable =
                Observable.concat(serverObservable, localObservable).filter(person -> person.getAge() > 10);

        concatFilterObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(person -> adapter.add(person));
    }

    @SuppressLint("CheckResult")
    void doZip() {
        Observable<ArrayList<Person>> serverObservable = createServerObservable();
        Observable<ArrayList<Person>> localObservable = createLocalObservable();
        // Zip two emissions together
        Observable<ArrayList<Person>> concatFilterObservable =
                Observable.zip(serverObservable, localObservable, (listPerson1, listPerson2) -> {
                    listPerson1.addAll(listPerson2);
                    return listPerson1;
                });

        concatFilterObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listPerson -> adapter.addAll(listPerson));
    }

    @SuppressLint("CheckResult")
    void doCombineLatest() {
        Observable<ArrayList<Person>> serverObservable = createServerObservable();
        Observable<ArrayList<Person>> localObservable = createLocalObservable();
        // Because we have only two emissions, so combine latest acts exactly the same as zip
        Observable<ArrayList<Person>> combineLatestObservable =
                Observable.combineLatest(serverObservable, localObservable, (list, list2) -> {
                    list.addAll(list2);
                    return list;
                });

        combineLatestObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listPerson -> adapter.addAll(listPerson));
    }

    @SuppressLint("CheckResult")
    void doFlatMap() {
        Observable<ArrayList<Person>> serverObservable = createServerObservable();
        Observable<ArrayList<Person>> localObservable = createLocalObservable();
        // Concat two emission of ArrayList<person>, then flatMap them into persons
        Observable<Person> flatMapObservable =
                Observable.concat(serverObservable, localObservable)
                .flatMap(Observable::fromIterable);

        // Then flatMap again to transform persons into custom persons (which have gender now)
        flatMapObservable.flatMap((Function<Person, ObservableSource<CustomPerson>>) person
                -> Observable.just(new CustomPerson(person.getName(), person.getAge())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(person -> adapter.add(person));
    }

    Observable<ArrayList<Person>> createServerObservable() {
        return Observable.create(emitter -> {
            ArrayList<Person> listPerson = new HttpRequestJson(urlString).responseToListPerson();
            // Emit a list of persons which is from online server
            emitter.onNext(listPerson);
            emitter.onComplete();
        });
    }

    Observable<ArrayList<Person>> createLocalObservable() {
        return Observable.create(emitter -> {
            ArrayList<Person> listPerson = arrayStringToPersonList(getResources().getString(R.string.sample_info_local));
            // Emit a list of persons which is from local
            emitter.onNext(listPerson);
            emitter.onComplete();
        });
    }

    public ArrayList<Person> arrayStringToPersonList(String str) {
        ArrayList<Person> listPerson = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(str);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject o = jsonArray.getJSONObject(i);
                Person person = new Person(o.getString("name"), o.getInt("age"));
                listPerson.add(person);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listPerson;
    }
}
