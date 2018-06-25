package com.sonphan12.examplemvvm.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sonphan12.examplemvvm.data.model.Joke;

public class JokeList{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("value")
    @Expose
    private List<Joke> jokes = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public JokeList() {
    }

    /**
     *
     * @param jokes
     * @param type
     */
    public JokeList(String type, List<Joke> jokes) {
        super();
        this.type = type;
        this.jokes = jokes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Joke> getJokes() {
        return jokes;
    }

    public void setJokes(List<Joke> jokes) {
        this.jokes = jokes;
    }

}
