package com.example.lap10255.examplerxjava2loaddata;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


public class HttpRequestJson {
    private static final String TAG = "HTTP_REQUEST_JSON";
    private String urlString;

    public HttpRequestJson(String urlString) {
        this.urlString = urlString;
    }
    private String responseString() {
        URL url;
        try {
            url = new URL(urlString);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
            return "";
        }
    }
    public ArrayList<Person> responseToListPerson() {
        ArrayList<Person> listPerson = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(responseString());
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
